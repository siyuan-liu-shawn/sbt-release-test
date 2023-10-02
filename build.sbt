lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-example-project",
    description := "Example sbt project that compiles using Scala 3",
    scalaVersion := "3.3.1",
    scalacOptions ++= Seq("-deprecation"),
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )

// bump the version and append '-SNAPSHOT', eg. 1.2.1 -> 1.2.2
releaseVersionBump := sbtrelease.Version.Bump.Next

// strip the qualifier off the input version, eg. 1.2.1-SNAPSHOT -> 1.2.1
releaseVersion     := { ver => sbtrelease.Version(ver).map(_.withoutQualifier.string).getOrElse(sbtrelease.versionFormatError(ver)) }

releaseNextVersion := {
    ver => sbtrelease.Version(ver).map(_.bump(releaseVersionBump.value).asSnapshot.string).getOrElse(sbtrelease.versionFormatError(ver))
}

import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    //publishArtifacts,
    setNextVersion,
    commitNextVersion,
    //pushChanges
)
