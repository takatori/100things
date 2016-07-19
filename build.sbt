name := """100things"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.softwaremill.macwire" %% "macros" % "2.2.3" % "provided",
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "org.specs2" %% "specs2-core" % "3.8.4" % "test",
  "org.mockito" % "mockito-all" % "1.9.5"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

scalacOptions in Test ++= Seq("-Yrangepos")

fork in run := true