package com.takatori.hundredthings.dao

import com.takatori.hundredthings.models.{User, UserTable}
import com.takatori.hundredthings.modules.DaoModule
import com.takatori.hundredthings.modules.MockDatabaseModule
import org.specs2.specification.{After, Scope}

import scala.concurrent.Await
import scala.concurrent.duration._

trait DaoContext extends MockDatabaseModule with DaoModule with Scope with After {

  import dbConfig.driver.api._
  val db = dbConfig.db
  val Users = TableQuery[UserTable]

  val setup = DBIO.seq(
    sqlu"DROP ALL OBJECTS",
    // Create the tables
    (Users.schema).create,
    // Insert some users
    Users += User(None, "takatori"),
    Users += User(None, "satoshi")
  )

  Await.result(db.run(setup), 30 seconds)

  def after = {
    println("teardown test")
    val teardown = DBIO.seq(sqlu"DROP ALL OBJECTS")
    Await.result(db.run(teardown), 30 seconds)
  }
}