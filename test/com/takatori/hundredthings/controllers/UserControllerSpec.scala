package com.takatori.hundredthings.controllers

import com.takatori.hundredthings.models.User
import org.specs2.mutable.Specification
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Future

/**
  * Created by takatorisatoshi on 2016/07/20.
  */
class UserControllerSpec extends Specification {
  "User Controller" should {
    "return all" in new ControllerContext {
      val users = Seq(
        User(1, "takatori"),
        User(2, "satoshi")
      )
      userDao.all returns Future.successful(users)
      val response = userController.fetchAll()(FakeRequest())

      status(response) must be equalTo OK
      val json = contentAsJson(response)

      json mustEqual Json.toJson(users)
    }
  }

  "Create user" in new ControllerContext {

  }
}
