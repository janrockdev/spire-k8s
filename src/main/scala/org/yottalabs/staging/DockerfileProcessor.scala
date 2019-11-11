package org.yottalabs.staging

import org.yottalabs.DockerfileLike
import sbt._
import sbtdocker.DockerfileLike

trait DockerfileProcessor {
  def apply(dockerfile: DockerfileLike, stageDir: File): StagedDockerfile
}
