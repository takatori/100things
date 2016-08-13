package com.takatori.hundredthings

import org.specs2.execute.{AsResult, Result}
import org.specs2.mutable.Around
import org.specs2.specification.Scope
import play.api._
import play.api.db.slick.{SlickApi, SlickComponents}
import play.api.test.Helpers

object TestEnvironment {

  val dbTestConf: Configuration = Configuration.from(
    Map(
      "slick.dbs.default.driver" -> "slick.driver.H2Driver$",
      "slick.dbs.default.db.driver" -> "org.h2.Driver",
      "slick.dbs.default.db.url" -> "jdbc:h2:mem:play;MODE=MYSQL",
      "play.evolutions.enabled" -> "true",
      "play.evolutions.autoApply" -> "true"
    )
  )

  def initAppComponents: AppComponents = {
    val context = ApplicationLoader.createContext(
      new Environment(new java.io.File("."), ApplicationLoader.getClass.getClassLoader, Mode.Test)
    )
    (new BuiltInComponentsFromContext(context) with AppComponents with SlickComponents)
  }

  class WithApplicationComponents extends Scope {
    implicit lazy val appComponents:AppComponents = initAppComponents
    implicit lazy val app:play.api.Application = appComponents.application
    implicit lazy val api:SlickApi = appComponents.api

    // Afterとコンフリクトするのでコメントアウト
    /*
    def around[T: AsResult](t: => T): Result = {
      // Executes a block of code in a running application.
      Helpers.running(app)(AsResult.effectively(t))
    }*/
  }
}