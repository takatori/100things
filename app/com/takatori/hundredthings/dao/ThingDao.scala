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

}