name := "PlayScala"

version := "1.0"

organization := "com.yundai"

scalaVersion := "2.11.7"

resolvers ++= Seq(
  "sonatype-snapshot" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "typesafe" at "http://repo.typesafe.com/typesafe/leases/"
)

fork := true