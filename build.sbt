lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-example-project",
    description := "Example sbt project that compiles using Scala 3",
    scalaVersion := "3.3.1",
    scalacOptions ++= Seq("-deprecation"),
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )

releaseVersionBump := sbtrelease.Version.Bump.Next

releaseProcess := Seq[ReleaseStep](
    sbtrelease.ReleaseStateTransformations.checkSnapshotDependencies,
    // sbtrelease.ReleaseStateTransformations.inquireVersions,
    sbtrelease.ReleaseStateTransformations.setReleaseVersion,
    sbtrelease.ReleaseStateTransformations.commitReleaseVersion,
    sbtrelease.ReleaseStateTransformations.tagRelease,
    sbtrelease.ReleaseStateTransformations.setNextVersion,
    sbtrelease.ReleaseStateTransformations.commitNextVersion,
    //sbtrelease.ReleaseStateTransformations.pushChanges
)
