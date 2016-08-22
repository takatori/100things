package com.takatori.hundredthings.controllers

import com.takatori.hundredthings.TestEnvironment.WithApplicationComponents
import com.takatori.hundredthings.models.User
import org.specs2.mutable.Specification
import play.api.libs.json.Json
import play.api.test.{FakeHeaders, FakeRequest, Helpers}
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

    "return an user by user_id" in new ControllerContext {
      val user = Some(User(1, "takatori"))
      userDao.fetch(1) returns Future.successful(user)

      val response = userController.fetch(1)(FakeRequest())

      status(response) must be equalTo OK
      val json = contentAsJson(response)

      json mustEqual Json.toJson(user)
    }

    "create user" in new WithApplicationComponents with ControllerContext {
      userDao.insert(any) returns Future.successful(1) // mock

      val payload = Json.parse("""{ "id": 1 , "name": "test" }""")
      val fakeRequest = FakeRequest(Helpers.POST, "/users", FakeHeaders(Seq("Content-Type" -> "application/json")), payload)
      val response = userController.insert()(fakeRequest)
      //val response = result.run() // http://stackoverflow.com/questions/35685066/how-to-test-a-controller-method-that-uses-a-custom-parser-in-play-2-5
      status(response) must be equalTo OK
      val json = contentAsJson(response)
      println(json)
    }

    "delete user" in new WithApplicationComponents with ControllerContext {
        userDao.delete(any) returns Future.successful(1) // mock

      val response = userController.delete(1)(FakeRequest())

      status(response) must be equalTo OK
    }
  }
}
