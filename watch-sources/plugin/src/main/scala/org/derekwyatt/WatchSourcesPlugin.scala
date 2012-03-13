package org.derekwyatt

import sbt._
import sbt.Keys._

object WatchSourcesPlugin extends Plugin {
  import Keys._
  val watchedFiles = TaskKey[Seq[File]]("watched-files", "The files to watch in dir-to-watch.")
  val echoFiles = TaskKey[Unit]("echo-files", "Task that prints the list of files that have changed.")
  val WatchConfig = config("watch")
  def watchSourcesSettings = Seq(
    dirToWatch <<= sourceDirectory / "watching",
    watchedFiles <<= dirToWatch map { d => { IO.listFiles(d).toSeq } },
    watchSources <++= watchedFiles,
    echoFiles <<= echoFilesTask,
    compile in Compile <<= compile in Compile dependsOn echoFiles
  )
  def echoFilesTask = (watchedFiles, target) map { (files, dir) =>
    files foreach { file =>
      val targetFile = dir / file.getName
      if (!targetFile.exists || targetFile.lastModified < file.lastModified) {
        println("===> %s changed".format(file.getName))
        IO.touch(targetFile)
      }
    }
  }
}
