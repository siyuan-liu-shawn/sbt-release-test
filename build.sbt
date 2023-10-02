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
// bump the version, eg. 1.2.1 -> 1.2.2
releaseVersionBump := Version.Bump.Next

// strip the qualifier off the input version, eg. 1.2.1-SNAPSHOT -> 1.2.1
releaseVersion     := { ver => sbtrelease.Version(ver).map(_.withoutQualifier.string).getOrElse(versionFormatError(ver)) }

// bump the version and append '-SNAPSHOT'
releaseNextVersion := {
    ver => Version(ver).map(_.bump(releaseVersionBump.value).asSnapshot.string).getOrElse(versionFormatError(ver))
}

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
