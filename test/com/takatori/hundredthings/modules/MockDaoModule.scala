package com.takatori.hundredthings.modules

import com.takatori.hundredthings.dao.{ThingDao, UserDao}
import org.specs2.mock.Mockito

trait MockDaoModule extends Mockito {
  lazy val userDao  = mock[UserDao]
  lazy val thingDao = mock[ThingDao]
}