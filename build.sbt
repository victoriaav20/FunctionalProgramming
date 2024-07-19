lazy val core = (project in file("core"))
  .settings(
    name := "core",
    scalaVersion := "2.13.10",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.0",
      "dev.zio" %% "zio-json" % "0.6.0",
      "org.scalatest" %% "scalatest" % "3.2.9" % Test 
    )
  )


lazy val app = (project in file("app"))
  .dependsOn(core)
  .settings(
    name := "GraphApp",
    scalaVersion := "2.13.10",
    mainClass := Some("app.MainApp") 
  )

lazy val root = (project in file("."))
  .aggregate(core, app)
  .settings(
    name := "FunctionalGraphs",
    mainClass := Some("app.MainApp")
  )
