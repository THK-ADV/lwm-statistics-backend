name := """LWMStatistics"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, PlayEbean)

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test
libraryDependencies += jdbc
libraryDependencies += ws
libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"
libraryDependencies += "com.typesafe.play" % "play-json-joda_2.12" % "2.6.0-RC1"
