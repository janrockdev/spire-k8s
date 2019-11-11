sbtPlugin := true

name := "spire-k8s"
organization := "org.yottalabs"
organizationHomepage := Some(url("https://github.com/janrock-git"))

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.3" % "test",
  "org.apache.commons" % "commons-lang3" % "3.5"
)

scalacOptions := Seq("-deprecation", "-unchecked", "-feature")

licenses := Seq("MIT License" -> url("https://github.com/janrock-git"))
homepage := Some(url("https://github.com/janrock-git"))
scmInfo := Some(ScmInfo(url("https://github.com/janrock-git"), "scm:git:git://github.com:janrock-git/.git"))

publishMavenStyle := true
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

pomIncludeRepository := { _ => false}

pomExtra := {
  <developers>
    <developer>
      <id>janrock</id>
      <name>Jan Rock</name>
      <url>https://github.com/janrock-git</url>
    </developer>
  </developers>
}

assemblyJarName := "spire-k8s.jar"

// Merging strategy
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

fork in run := true
javaOptions in run ++= Seq(
  "-Dlog4j.debug=true",
  "-Dlog4j.configuration=log4j.properties")
outputStrategy := Some(StdoutOutput)