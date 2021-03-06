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

  def insert(user: User): Future[Int]  = db.run((Users returning Users.map(_.id)) += user)

  def update(userId: Int, user: User): Future[Int]  = db.run(Users.filter(_.id === userId).map(u => u.name).update(user.name))

  def delete(userId: Int): Future[Int] = db.run(Users.filter(_.id === userId).delete) // return affected rows count
}
