package com.takatori.hundredthings

import com.takatori.hundredthings.dao.UserDao
import com.takatori.hundredthings.models.{User, UserTable}
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class Seed(val dbConfig: DatabaseConfig[JdbcProfile], val userDao: UserDao)
{
  import dbConfig.driver.api._
  val db = dbConfig.db
  val Users = TableQuery[UserTable]

  def run(): Unit = {
    val setup = DBIO.seq(
      // Create the tables, including primary and foreign keys
      sqlu"DROP ALL OBJECTS",
      (Users.schema).create,

      // Insert some users
      Users += User(1, "takatori"),
      Users += User(2, "satoshi")
    )
    Await.result(db.run(setup), 30 seconds)
  }
}