package com.takatori.hundredthings.dao

import com.takatori.hundredthings.TestEnvironment.WithApplicationComponents
import com.takatori.hundredthings.models.User
import org.specs2.concurrent.ExecutionEnv
import org.specs2.mutable.Specification

class UserDAOSpec(implicit ee: ExecutionEnv) extends Specification {

  "UserDAO" should {
    "return all" in new WithApplicationComponents with DaoContext { }

    "fetch an user by id" in new WithApplicationComponents with DaoContext {
      userDao.fetch(1) must beEqualTo(Some(User(Some(1), "takatori"))).await
    }

    "create user" in new WithApplicationComponents with DaoContext {
      userDao.insert(User(None, "specs2")) must beEqualTo(3).await
    }

    "update user" in new WithApplicationComponents with DaoContext {
      userDao.update(1, User(None, "takatori2")) must beEqualTo(1).await
    }

    "delete user" in new WithApplicationComponents with DaoContext {
      userDao.delete(1) must beEqualTo(1).await
    }
  }
}

