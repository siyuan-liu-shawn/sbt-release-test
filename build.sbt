lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-example-project",
    description := "Example sbt project that compiles using Scala 3",
    scalaVersion := "3.3.1",
    scalacOptions ++= Seq("-deprecation"),
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )

import ReleaseTransformations.*
import sbtrelease.*

releaseVersionBump := (System.getenv("RELEASE_TYPE") match {
    case "Major" => Version.Bump.Major
    case "Minor" => Version.Bump.Minor
    case "Bugfix" => Version.Bump.Bugfix
    case "Nano" => Version.Bump.Nano
    case _ => Version.Bump.Next // eg. 1.2.1 -> 1.2.2
})

// Customize the release process
releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    setNextVersion,
    commitNextVersion,
    pushChanges
)
