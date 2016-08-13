package com.takatori.hundredthings.modules

import _root_.slick.driver.JdbcProfile
import play.api.db.slick.{DbName, SlickApi }
import slick.backend.DatabaseConfig

trait MockDatabaseModule {
  def api: SlickApi
  // SlickComponentsがapiフィールドを持つ
  // SlickComponentsは抽象フィールド, environment configuration applicationLifeCycleをもつ
  lazy val dbConfig: DatabaseConfig[JdbcProfile] = api.dbConfig[JdbcProfile](DbName("test"))
}