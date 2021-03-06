package com.takatori.hundredthings.dao

import com.takatori.hundredthings.models.{Thing, ThingTable}
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

import scala.concurrent.Future

class ThingDao(dbConfig: DatabaseConfig[JdbcProfile]) {
  import dbConfig.driver.api._

  val db = dbConfig.db
  val Things = TableQuery[ThingTable]

  def list(userId: Int): Future[Seq[Thing]] = db.run(Things.filter(_.userId === userId).result)

  def fetch(id: Int): Future[Option[Thing]] = db.run(Things.filter(_.id === id).result.headOption)

  def create(userId: Int, thing: Thing): Future[Int] = db.run((Things returning Things.map(_.id)) += thing)

  def update(thingId: Int, thing: Thing): Future[Int] =
    db.run(
      Things.filter(_.id === thingId)
            .map(t => (t.title, t.description, t.done))
            .update(thing.title, thing.description, thing.done))

  def delete(thingId: Int): Future[Int] = db.run(Things.filter(_.id === thingId).delete)
}