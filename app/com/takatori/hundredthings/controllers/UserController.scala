package com.takatori.hundredthings.controllers

import com.takatori.hundredthings.dao.UserDao
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext

class UserController(userDao: UserDao)(implicit ec: ExecutionContext) extends Controller {

  def fetchAll() = Action.async { request =>
    userDao.all.map { users =>
      Ok(Json.toJson(users))
    }
  }

}