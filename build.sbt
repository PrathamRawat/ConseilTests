name := "ConseilTest"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
    "org.http4s" %% "http4s-blaze-client" % "0.20.22",
    "org.http4s" %% "http4s-dsl" % "0.20.22",
)

libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "1.7.5",
    "org.slf4j" % "slf4j-simple" % "1.7.5")
