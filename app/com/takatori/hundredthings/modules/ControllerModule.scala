package com.takatori.hundredthings.modules

import com.softwaremill.macwire._
import com.takatori.hundredthings.controllers.{ThingController, UserController}
import com.takatori.hundredthings.dao.{ThingDao, UserDao}

import scala.concurrent.ExecutionContext

/**
  * Created by takatorisatoshi on 2016/07/16.
  */
trait ControllerModule {
  // Dependencies
  implicit def ec: ExecutionContext
  def userDao: UserDao
  def thingDao: ThingDao

  lazy val userController: UserController   = wire[UserController]
  lazy val thingController: ThingController = wire[ThingController]
}
