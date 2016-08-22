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

  def fetch(userId: Int): Future[Option[User]] = db.run(Users.filter(_.id === userId).result.headOption)

  def insert(user: User): Future[Int] = db.run((Users returning Users.map(_.id)) += user)

  def delete(userId: Int): Future[Int] = {
    val q = Users.filter(_.id === userId)
    val action = q.delete
    val affectedRowsCount: Future[Int] = db.run(action)
    affectedRowsCount
  }
}