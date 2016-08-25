package com.takatori.hundredthings.modules

import com.softwaremill.macwire._
import com.takatori.hundredthings.dao.{ThingDao, UserDao}
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

trait DaoModule {
  def dbConfig: DatabaseConfig[JdbcProfile]

  lazy val userDao: UserDao   = wire[UserDao]
  lazy val thingDao: ThingDao = wire[ThingDao]
}