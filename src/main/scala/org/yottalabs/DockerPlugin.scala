package org.yottalabs

import org.yottalabs
import org.yottalabs.mutable.Dockerfile
import org.yottalabs.staging.CopyFile
import sbt._

object DockerPlugin extends AutoPlugin {
  object autoImport {
    val DockerKeys = yottalabs.DockerKeys

    val docker = DockerKeys.docker
    val dockerfile = DockerKeys.dockerfile
    val dockerPath = DockerKeys.dockerPath
    @deprecated("Use imageNames instead.", "1.0.0")
    val imageName = DockerKeys.imageName
    val imageNames = DockerKeys.imageNames
    val buildOptions = DockerKeys.buildOptions

    type Dockerfile = Dockerfile
    val ImageId = yottalabs.ImageId
    type ImageId = yottalabs.ImageId
    val ImageName = yottalabs.ImageName
    type ImageName = yottalabs.ImageName
    val BuildOptions = yottalabs.BuildOptions
    type BuildOptions = yottalabs.BuildOptions

    val CopyFile = CopyFile
    type CopyFile = staging.CopyFile

    /**
     * Settings to automatically build a Docker image for a JVM application.
     * @param fromImage Base image to use. Should have a JVM on the PATH.
     * @param exposedPorts List of ports to expose.
     * @param exposedVolumes List of volumes to expose.
     * @param username Username that should run the Java process.
     */
    def dockerAutoPackageJavaApplication(fromImage: String = "java:8-jre",
                                         exposedPorts: Seq[Int] = Seq.empty,
                                         exposedVolumes: Seq[String] = Seq.empty,
                                         username: Option[String] = None): Seq[sbt.Def.Setting[_]] = {
      DockerSettings.autoPackageJavaApplicationSettings(fromImage, exposedPorts, exposedVolumes, username)
    }
  }

  override def projectSettings = DockerSettings.baseDockerSettings
}
