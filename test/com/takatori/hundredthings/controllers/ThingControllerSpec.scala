package com.takatori.hundredthings.controllers

import com.takatori.hundredthings.models.Thing
import org.joda.time.DateTime
import org.specs2.mutable.Specification
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Future


class ThingControllerSpec extends Specification {

  "Thing Controller" should {
    "return things by userId" in new ControllerContext {
      val things = Seq(
        Thing(Some(1), 1, "test", Some("test"), false, Some(new DateTime())),
        Thing(Some(2), 2, "test2", None, true, None)
      )
      thingDao.list(1) returns Future.successful(things)

      val response = thingController.list(1)(FakeRequest())

      status(response) must be equalTo OK
      val json = contentAsJson(response)

      json mustEqual Json.toJson(things)
    }

    "return the thing by id" in new ControllerContext {
      val thing = Thing(Some(1), 1, "test", Some("test"), false, Some(new DateTime()))

      thingDao.fetch(1) returns Future.successful(Some(thing))

      val response = thingController.fetch(1)(FakeRequest())

      status(response) must be equalTo OK
      val json = contentAsJson(response)

      json mustEqual Json.toJson(thing)
    }
  }
}