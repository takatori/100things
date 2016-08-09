package com.takatori.hundredthings.modules

import _root_.slick.driver.JdbcProfile
import play.api.db.slick.DbName

trait MockDatabaseModule {
  lazy val dbConfig = dbConfig[JdbcProfile](DbName("test"))
}