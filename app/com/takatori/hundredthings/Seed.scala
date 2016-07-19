package com.takatori.hundredthings

import com.takatori.hundredthings.dao.UserDao
import com.takatori.hundredthings.models.User
import slick.backend.DatabaseConfig // A configuration for a Database plus a matching Slick driver.
import slick.driver.JdbcProfile // A profile for accessing SQL databases via JDBC. All drivers for JDBC-based databases implement this profile.

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class Seed(
          val dbConfig: DatabaseConfig[JdbcProfile],
          val userDao: UserDao)
{
  import dbConfig.driver.api._
  val db = dbConfig.db

  def run(): Unit = {
    val setup = DBIO.seq(
      // Create the tables, including primary and foreign keys
      sqlu"DROP ALL OBJECTS",
      (userDao.query.schema).create,

      // Insert some users
      userDao.query += User(1, "takatori"),
      userDao.query += User(2, "satoshi")
    )
    Await.result(db.run(setup), 30 seconds)
  }
}