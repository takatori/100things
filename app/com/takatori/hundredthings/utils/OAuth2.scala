package com.takatori.hundredthings.utils

import play.api.Configuration
import play.api.http.{HeaderNames, MimeTypes}
import play.api.inject.ApplicationLifecycle
import play.api.libs.ws.ahc.{AhcConfigBuilder, AhcWSClient}
import play.api.mvc.Results

import scala.concurrent.Future

class OAuth2(configuration: Configuration, lifecycle: ApplicationLifecycle) {

  lazy val authId     = configuration.getString("github.client.id")
  lazy val authSecret = configuration.getString("github.client.secret")
  lazy val baseURL    = configuration.getString("github.redirect.url")

  def getAuthorizationURL(redirectURL: String, scope: String, state: String): Option[String] = {
    for {
      id <- authId
      url <- baseURL
    } yield url.format(id, redirectURL, scope, state)
  }

  def getToken(code: String): Future[String] = {
    val client = new AhcWSClient(new AhcConfigBuilder().configure().build())

    val tokenResponse = client.url("http://example.com/feed")
      .withQueryString("client_id" -> authId.getOrElse(""), "client_secret" -> authSecret.getOrElse(""), "code" -> code)
      .withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON)
      .post(Results.EmptyContent())

    val result = tokenResponse.flatMap { response =>
      (response.json \ "access_token").asOpt[String]
        .fold(Future.failed[String](new IllegalStateException("Sod off!"))) { accessToken =>
        Future.successful(accessToken)
      }
    }

    lifecycle.addStopHook(() => {
      client.close()
      Future.successful()
    })

    result
  }
}