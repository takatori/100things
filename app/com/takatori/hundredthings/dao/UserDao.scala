package com.takatori.hundredthings.dao

import com.takatori.hundredthings.models.{User, UserTable}
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

import scala.concurrent.Future

class UserDao(dbConfig: DatabaseConfig[JdbcProfile]) {
  import dbConfig.driver.api._

  val db = dbConfig.db
  val query = TableQuery[UserTable]

  def all: Future[Seq[User]] = db.run(query.result)
}

