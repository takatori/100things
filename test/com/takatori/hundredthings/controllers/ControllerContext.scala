package com.takatori.hundredthings.controllers

import com.takatori.hundredthings.modules.{ControllerModule, MockDaoModule}
import org.specs2.matcher.MustThrownExpectations
import org.specs2.specification.Scope

import scala.concurrent.ExecutionContext

trait ControllerContext
extends ControllerModule
with MockDaoModule
with Scope
with MustThrownExpectations
{
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
}