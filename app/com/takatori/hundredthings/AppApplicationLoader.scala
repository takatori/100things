package com.takatori.hundredthings

import _root_.controllers.Assets
import com.takatori.hundredthings.modules.{ControllerModule, DaoModule, DatabaseModule}
import play.api.ApplicationLoader.Context
import play.api._
import com.softwaremill.macwire._
import com.takatori.hundredthings.utils.OAuth2
import play.api.routing.Router

import scala.concurrent.ExecutionContext

/**
  * Created by takatorisatoshi on 2016/07/15.
  */
class AppApplicationLoader extends ApplicationLoader {
  def load(context: Context) = {
    LoggerConfigurator(context.environment.classLoader).foreach { _.configure(context.environment)}
    new AppComponents(context).application
  }
}

class AppComponents(context: Context) extends BuiltInComponentsFromContext(context) with DatabaseModule with DaoModule with ControllerModule with OAuth2
{
    implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
    lazy val assets: Assets = wire[Assets]
    lazy val router: Router = {
      // add the prefix string in local scope for the Routes constructor
      val prefix: String = "/"
      wire[Routes]
    }
}
