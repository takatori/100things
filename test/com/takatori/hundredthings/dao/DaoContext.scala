package com.takatori.hundredthings.dao

import com.takatori.hundredthings.models.{Thing, ThingTable, User, UserTable}
import com.takatori.hundredthings.modules.DaoModule
import com.takatori.hundredthings.modules.MockDatabaseModule
import org.specs2.specification.{After, Scope}

import scala.concurrent.Await
import scala.concurrent.duration._

trait DaoContext extends MockDatabaseModule with DaoModule with Scope with After {

  import dbConfig.driver.api._
  val db = dbConfig.db
  val Users  = TableQuery[UserTable]
  val Things = TableQuery[ThingTable]

  val setup = DBIO.seq(
    sqlu"DROP ALL OBJECTS",
    // Create the tables
    (Users.schema).create,
    (Things.schema).create,

    // Insert some users
    Users += User(None, "takatori"),
    Users += User(None, "satoshi"),
    Things += Thing(None, 1, "test thing", Some("test description"), false, None),
    Things += Thing(None, 2, "test2", None, true, None)
  )

  Await.result(db.run(setup), 30 seconds)

  def after = {
    println("teardown test")
    val teardown = DBIO.seq(
      sqlu"DROP ALL OBJECTS",
      sqlu"ALTER TABLE User ALTER COLUMN id RESTART WITH 1",
      sqlu"ALTER TABLE Thing ALTER COLUMN id RESTART WITH 1"
    )
    Await.result(db.run(teardown), 30 seconds)
  }
}