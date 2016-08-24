package com.takatori.hundredthings.controllers

import com.takatori.hundredthings.dao.ThingDao
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext

class ThingController(thingDao: ThingDao)(implicit ec: ExecutionContext) extends Controller {

  def list(userId: Int) = Action.async { request =>
    thingDao.list(userId) map { things => Ok(Json.toJson(things))}
  }
}