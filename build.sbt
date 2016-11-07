name := "finatra-http-example"
description := "Example of REST server with finatra"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.11.8"

resolvers += Resolver.url("Twitter", url("http://maven.twttr.com/"))
resolvers += Resolver.url("Typesafe Releases", url("http://repo.typesafe.com/typesafe/releases/"))
resolvers += Resolver.url("One", url("https://repo1.maven.org/maven2/"))
resolvers += Resolver.url("Mvn", url("https://mvnrepository.com/"))

lazy val versions = new {
  val config = "1.3.1"
  val finatra = "2.5.0"
  val swagger = "0.6.0"
  val jwt = "0.4.1"

  val logback = "1.1.7"
}

val baseDependencies = Seq(
  "com.typesafe" % "config" % versions.config,
  "ch.qos.logback" % "logback-classic" % versions.logback
)

val finatraDependencies = Seq(
  "com.twitter" %% "finatra-http" % versions.finatra,
  "com.twitter" %% "finatra-jackson" % versions.finatra,

  "com.github.xiaodongw" %% "swagger-finatra" % versions.swagger exclude("com.twitter", "finatra-http"),
  "com.jason-goodwin" %% "authentikat-jwt" % versions.jwt
)

libraryDependencies ++= baseDependencies union finatraDependencies