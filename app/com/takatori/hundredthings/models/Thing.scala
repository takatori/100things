package com.takatori.hundredthings.models

import com.takatori.hundredthings.models.UserTable
import org.joda.time.DateTime
import play.api.libs.json.Json
import slick.driver.H2Driver.api._
import com.github.tototoshi.slick.H2JodaSupport._

case class Thing (
  id: Option[Int],
  userId: Int,
  title: String,
  description: Option[String],
  done: Boolean,
  createdAt: Option[DateTime])

object Thing {
  implicit val format = Json.format[Thing]
}

class ThingTable(tag: Tag) extends Table[Thing](tag, "Thing") {

  val users       = TableQuery[UserTable]

  def id          = column[Int]("THING_ID", O.PrimaryKey, O.AutoInc)
  def userId      = column[Int]("USER_ID")
  def title       = column[String]("TITLE")
  def description = column[Option[String]]("DESCRIPTION")
  def done        = column[Boolean]("DONE")
  def createdAt   = column[Option[DateTime]]("CREATED_AT", O.Default(Some(new DateTime())))
  def *           = (id.?, userId, title, description, done, createdAt) <> ((Thing.apply _).tupled, Thing.unapply)
  def user        = foreignKey("USER_KEY", userId, users)(_.id, onDelete=ForeignKeyAction.Cascade)
}