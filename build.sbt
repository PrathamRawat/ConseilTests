name := "ConseilTest"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
    "org.http4s" %% "http4s-blaze-client" % "0.20.22",
    "org.http4s" %% "http4s-dsl" % "0.20.22",
)

libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % "1.7.5",
    "org.slf4j" % "slf4j-simple" % "1.7.5"
)

val circeVersion = "0.11.1"

libraryDependencies ++= Seq(
    "io.circe" %% "circe-core" % "0.11.1",
    "io.circe" %% "circe-generic" % "0.11.1",
    "io.circe" %% "circe-parser" % "0.11.1"
)

val json4sNative = "org.json4s" %% "json4s-native" % "3.6.10"
