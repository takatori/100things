package com.takatori.hundredthings.modules

import com.takatori.hundredthings.dao.UserDao
import org.specs2.mock.Mockito

trait MockDaoModule extends Mockito {
  lazy val userDao = mock[UserDao]
}