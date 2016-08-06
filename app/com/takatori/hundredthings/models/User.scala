package com.takatori.hundredthings.models

import play.api.libs.json.Json
import slick.driver.H2Driver.api._

/**
  * Created by takatorisatoshi on 2016/07/17.
  */
case class User (
  id: Int,
  name: String)

object User {
  implicit val format = Json.format[User]
}

class UserTable(tag: Tag) extends Table[User](tag, "User") {
  def id   = column[Int]("USER_ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")
  def *    = (id, name) <> ((User.apply _).tupled, User.unapply) //http://slick.lightbend.com/doc/3.1.0/schemas.html#mapped-tables
}