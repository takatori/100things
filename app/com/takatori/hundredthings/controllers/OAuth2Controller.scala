package com.takatori.hundredthings.controllers

import com.takatori.hundredthings.utils.OAuth2
import play.api.mvc.{Action, Controller}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by takatorisatoshi on 2016/09/01.
  */
class OAuth2Controller(oauth2: OAuth2)(implicit ec: ExecutionContext) extends Controller {

  def callback(codeOpt: Option[String], stateOpt: Option[String]) = Action.async {
    request =>
      (for {
        code <- codeOpt
        state <- stateOpt
        outhState <- request.session.get("oauth-state")
      } yield {
        if (state === outhState) {
          oauth2.getToken(code).map { accessToken =>
            Redirect(routes.OAuth2.success()).withSession()
          } recover {
            case ex: IllegalStateException => Unauthorized(ex.getMessage)
          }
        }
        else {
          Future.successful(BadRequest(""))
        }
      }).getOrElse(Future.successful(BadRequest("")))
  }
}
