package com.takatori.hundredthings

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import org.specs2.specification.Scope
import play.api._
import play.api.db.slick.{SlickApi, SlickComponents}

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
    implicit lazy val appComponents: AppComponents = initAppComponents
    implicit lazy val app: play.api.Application = appComponents.application
    implicit lazy val api: SlickApi = appComponents.api
    implicit lazy val actorSystem = ActorSystem()
    implicit lazy val materializer: Materializer = ActorMaterializer()

    // Afterとコンフリクトするのでコメントアウト
    /*
    def around[T: AsResult](t: => T): Result = {
      // Executes a block of code in a running application.
      Helpers.running(app)(AsResult.effectively(t))
    }*/
  }
}