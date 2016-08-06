package com.takatori.hundredthings.controllers

import com.takatori.hundredthings.dao.UserDao
import com.takatori.hundredthings.models.User
import play.api.libs.json.{JsError, JsResult, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext

class UserController(userDao: UserDao)(implicit ec: ExecutionContext) extends Controller {

  def fetchAll() = Action.async { request => userDao.all.map { users => Ok(Json.toJson(users)) } }

  def insert() = Action(parse.json) { request =>
    val userResult: JsResult[User] = request.body.validate[User]
    userResult.fold(
      errors => {
        BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))
      },
      user => {
        userDao.insert(user)
        Ok(Json.obj("status" -> "OK", "message" -> ("Place '" + user.name + "' saved.")))
      })
  }

  def delete(userId: Int) = Action.async { request =>
    userDao.delete(userId) map { result => Ok(Json.toJson("Succeed in delete user.")) }
  }
}