name := "PlayScala"

version := "1.0"

organization := "com.yundai"

scalaVersion := "2.11.7"

resolvers ++= Seq(
  "sonatype-snapshot" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "typesafe" at "http://repo.typesafe.com/typesafe/leases/"
)

libraryDependencies += "com.netflix.rxjava" % "rxjava-scala" % "0.20.7"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.3.2"
libraryDependencies += "commons-io" % "commons-io" % "2.4"
libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.3.2"

fork := false