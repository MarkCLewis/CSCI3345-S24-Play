import sbtcrossproject.{crossProject, CrossType}

lazy val server = (project in file("server")).settings(commonSettings).settings(
	name := "play-server",
  scalaJSProjects := Seq(client),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  libraryDependencies ++= Seq(
    "com.vmunier" %% "scalajs-scripts" % "1.1.2",
    guice,
		"org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
		"com.typesafe.play" %% "play-slick" % "5.0.0",
		"com.typesafe.slick" %% "slick-codegen" % "3.3.3",
		"com.typesafe.play" %% "play-json" % "2.9.1",
    specs2 % Test
  )
).enablePlugins(PlayScala).
  dependsOn(sharedJvm)

lazy val client = (project in file("client")).settings(commonSettings).settings(
	name := "play-client",
  scalaJSUseMainModuleInitializer := true,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "1.1.0",
		"me.shadaj" %%% "slinky-core" % "0.6.6",
		"me.shadaj" %%% "slinky-web" % "0.6.6",
		"com.typesafe.play" %% "play-json" % "2.9.1"
  ),
	addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(sharedJs)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(commonSettings)
	.settings(
		name := "play-shared",
		libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.1"
	)
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  scalaVersion := "2.12.12",
  organization := "edu.trinity"
)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen {s: State => "project server" :: s}