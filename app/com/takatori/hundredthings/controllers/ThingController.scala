package com.takatori.hundredthings.controllers

import com.takatori.hundredthings.dao.ThingDao
import com.takatori.hundredthings.models.Thing
import play.api.libs.json.{JsError, JsResult, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.{ExecutionContext, Future}

class ThingController(thingDao: ThingDao)(implicit ec: ExecutionContext) extends Controller {

  def list(userId: Int) = Action.async { request =>
    thingDao.list(userId) map { things => Ok(Json.toJson(things)) }
  }

  def fetch(id: Int) = Action.async { request =>
    thingDao.fetch(id) map { thing => Ok(Json.toJson(thing)) }
  }

  def create(userId: Int) = Action.async(parse.json) { request =>
    val thingResult: JsResult[Thing] = request.body.validate[Thing]
    thingResult.fold(
      errors => Future.successful(BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))),
      thing => {
        thingDao.create(userId, thing) map { id =>
          Ok(Json.obj("status" -> "OK", "message" -> ("Place" +  id + "saved.")))
        }
      }
    )
  }

  def delete(thingId: Int) = Action.async { request =>
    thingDao.delete(thingId) map { result => Ok(Json.toJson("Succeed in delete thing.")) }
  }
}