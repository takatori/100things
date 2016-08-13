package com.takatori.hundredthings.modules

import _root_.slick.driver.JdbcProfile
import play.api.db.slick.{DbName, SlickComponents}

trait DatabaseModule extends SlickComponents {
  lazy val dbConfig = api.dbConfig[JdbcProfile](DbName("default"))
}