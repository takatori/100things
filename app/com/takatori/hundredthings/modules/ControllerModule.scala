package com.takatori.hundredthings.modules

import com.softwaremill.macwire._
import com.takatori.hundredthings.controllers.UserController
import com.takatori.hundredthings.dao.UserDao

import scala.concurrent.ExecutionContext

/**
  * Created by takatorisatoshi on 2016/07/16.
  */
trait ControllerModule {
  // Dependencies
  implicit def ec: ExecutionContext
  def userDao: UserDao

  lazy val userController: UserController = wire[UserController]
}
