package com.takatori.hundredthings.dao

import com.takatori.hundredthings.TestEnvironment.WithApplicationComponents
import com.takatori.hundredthings.models.Thing
import org.specs2.concurrent.ExecutionEnv
import org.specs2.mutable.Specification

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class ThingDAOSpec(implicit ee: ExecutionEnv) extends Specification {

  "ThingDAO" should {
    "return things by userId" in new WithApplicationComponents with DaoContext {
      val things = Await.result(thingDao.list(1), Duration.Inf)
      things(0).id === Some(1)
      things(0).userId === 1
      things(0).title === "test thing"
      things(0).description === Some("test description")
      things(0).done === false
     }

    "return the thing by id" in new WithApplicationComponents with DaoContext {
      val thing = Await.result(thingDao.fetch(1), Duration.Inf).get
      thing.id === Some(1)
      thing.userId === 1
      thing.title === "test thing"
      thing.description === Some("test description")
      thing.done === false
    }

    "create thing" in new WithApplicationComponents with DaoContext {
      thingDao.create(1, Thing(None, 1, "test", Some("test"), false, None)) must beEqualTo(3).await
    }

    "update thing" in new WithApplicationComponents with DaoContext {
      thingDao.update(1, Thing(None, 1, "update", None, true, None)) must beEqualTo(1).await
    }

    "delete thing" in new WithApplicationComponents with DaoContext {
      thingDao.delete(1) must beEqualTo(1).await
    }
  }
}