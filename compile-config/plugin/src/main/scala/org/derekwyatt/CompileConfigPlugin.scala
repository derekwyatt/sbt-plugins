package org.derekwyatt

import sbt._
import sbt.Keys._

object CompileConfigPlugin extends Plugin {
  import Keys._
  val watchedFiles = TaskKey[Seq[File]]("watched-files", "The files to watch in dir-to-watch.")
  val compile = TaskKey[Unit]("compile", "Task that 'compiles' the files that have changed.")
  val NewConfig = config("new") extend(Compile)
  //def compileConfigSettingHelper = Seq(
  lazy val compileConfigSettings: Seq[Setting[_]] = inConfig(NewConfig)(Defaults.configSettings ++ Seq(
    dirToWatch <<= sourceDirectory / "watching",
    watchedFiles <<= dirToWatch map { d => { IO.listFiles(d).toSeq } },
    watchSources <++= watchedFiles,
    (compile in Compile) ~= { _ => compileTask }
  ))
  def compileTask: Unit = (watchedFiles, target) map { (files, dir) =>
    files foreach { file =>
      val targetFile = dir / file.getName
      if (!targetFile.exists || targetFile.lastModified < file.lastModified) {
        println("===> compiled %s".format(file.getName))
        IO.touch(targetFile)
      }
    }
  }
}
