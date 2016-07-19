package com.takatori.hundredthings.modules

import com.takatori.hundredthings.dao.UserDao
import org.mockito.Mockito

trait MockDaoModule extends Mockito {
  lazy val userDao = mock[UserDao]
}