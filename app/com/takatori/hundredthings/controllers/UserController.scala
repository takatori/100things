package com.takatori.hundredthings.controllers

import com.takatori.hundredthings.dao.UserDao
import com.takatori.hundredthings.models.User
import play.api.libs.json.{JsError, JsResult, Json}
import play.api.mvc.{Action, ActionBuilder, ActionTransformer, Controller, Request, WrappedRequest}

import scala.concurrent.{ExecutionContext, Future}


trait UserActionBuilder {

  class UserRequest[A](val user: Option[User], request: Request[A]) extends WrappedRequest[A](request)

  def userDao: UserDao

  def UserAction = new ActionBuilder[UserRequest] with ActionTransformer[UserRequest]{

    def transform[A](request: Request[A]): Future[UserRequest[A]] =
      for {
        userIdString <- request.session.get("user_id")
        userId <- userIdString.toInt
        user <- userDao.fetch(userId)
        userRequest <- new UserRequest(user, request)
      } yield userRequest match {
        case None => Future.failed(new Exception("Please Login"))
        case Some(r) => Future.successful(r)
      }
  }

}

class UserController(userDao: UserDao)(implicit ec: ExecutionContext) extends Controller {

  def fetchAll() = Action.async { request => userDao.all.map { users => Ok(Json.toJson(users)) } }

  def fetch(userId: Int) = Action.async { request =>
    userDao.fetch(userId) map { user => Ok(Json.toJson(user)) }
    //BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson("not found")))
  }

  def insert() = Action.async(parse.json) { request =>
    val userResult: JsResult[User] = request.body.validate[User]
    // Future[Result]型をかえす必要がある　
    userResult.fold(
      errors => Future.successful(BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))),
      user => {
        userDao.insert(user) map { id =>
            Ok(Json.obj("status" -> "OK", "message" -> ("Place '" + user.name + "' saved.")))
        }
      })
  }

  def update(userId: Int) = Action.async(parse.json) { request =>
    val userResult: JsResult[User] = request.body.validate[User]
    userResult.fold(
      errors => Future.successful(BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))),
      user => {
        userDao.update(userId, user) map { affetctedRows =>
          Ok(Json.obj("status" -> "OK", "message" -> ("Place '" + user.name + "' update.")))
        }
      })
  }

  def delete(userId: Int) = Action.async { request =>
    userDao.delete(userId) map { result => Ok(Json.toJson("Succeed in delete user.")) } // TODO: 失敗時の処理
  }
}
