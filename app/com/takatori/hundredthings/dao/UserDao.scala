package com.takatori.hundredthings.dao

import com.takatori.hundredthings.models.{User, UserTable}
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

import scala.concurrent.Future

class UserDao(dbConfig: DatabaseConfig[JdbcProfile]) {
  import dbConfig.driver.api._

  val db = dbConfig.db
  val Users = TableQuery[UserTable]

  def all: Future[Seq[User]] = db.run(Users.result)

  def insert(user: User): Future[Int] = db.run((Users returning Users.map(_.id)) += user)

  def update(user: User): Future[Int] = db.run(Users.filter(_.id === user.id).update(user))
}