package com.takatori.hundredthings.modules

import _root_.slick.driver.JdbcProfile
import play.api.db.slick.DbName
import play.api.db.slick.SlickComponents //

trait MockDatabaseModule extends SlickComponents {
  lazy val dbConfig = api.dbConfig[JdbcProfile](DbName("test"))
}