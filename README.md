spire-k8s
==========

Spire-k8s is an Sbt plugin that builds and pushes Docker images with Kubernetes configuration to your Dockerhub, Google Cloud Container Registry etc.  

Release: 0.0.1 (Initial Version)

Requirements
------------

* Sbt
* Docker (local installation on compile machine)

Setup
-----

Add sbt-docker as a dependency in `project/plugins.sbt`:
```scala
addSbtPlugin("org.yottalabs" % "spire-k8s" % "0.0.1")
```

sbt-docker is an auto plugin, this means that sbt version 1.3.3 or higher is required.

Java 8 or higher is required.

### Defining a Dockerfile

In order to produce a Docker image a Dockerfile must be defined.

Example with the sbt-assembly plugin:
```scala
enablePlugins(DockerPlugin)

imageNames in docker := Seq(ImageName("yottalabs/demo:1.0.0"))

dockerfile in docker := {
  val jarFile: File = sbt.Keys.`package`.in(Compile, packageBin).value
  val classpath = (managedClasspath in Compile).value
  val mainclass = mainClass.in(Compile, packageBin).value.getOrElse(sys.error("Expected exactly one main class"))
  val jarTarget = s"/app/${jarFile.getName}"
  // Make a colon separated classpath with the JAR file
  val classpathString = classpath.files.map("/app/" + _.getName)
    .mkString(":") + ":" + jarTarget
  new Dockerfile {
    // Base image
    from("openjdk:8-jre")
    // Add all files on the classpath
    add(classpath.files, "/app/")
    // Add the JAR file
    add(jarFile, jarTarget)
    // On launch run Java with the classpath and the main class
    entryPoint("java", "-cp", classpathString, mainclass)
    expose(9003)
  }
}
```

### Building an image

To build an image use the `docker` task.
Simply run `sbt docker` from your prompt or `docker` in the sbt console.

### Pushing an image

An image that have already been built can be pushed with the `dockerPush` task.
To both build and push an image use the `dockerBuildAndPush` task.

The `imageNames in docker` key is used to determine which image names to push.