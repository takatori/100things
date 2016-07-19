package com.takatori.hundredthings.modules

// _root_ 明示的に絶対指定する
import _root_.slick.driver.JdbcProfile
import play.api.db.slick.DbName // The name used in the application's config file to reference a slick database configuration.
import play.api.db.slick.SlickComponents //

trait DatabaseModule extends SlickComponents {
  lazy val dbConfig = api.dbConfig[JdbcProfile](DbName("default"))
}