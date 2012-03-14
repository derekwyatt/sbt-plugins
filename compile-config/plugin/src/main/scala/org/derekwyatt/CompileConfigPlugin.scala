package org.derekwyatt

import sbt._
import sbt.Keys._

object CompileConfigPlugin extends Plugin {
  import Keys._ // pulls in dirToWatch

  // Internal Task to hold the dynamic set of sources
  val newSources = TaskKey[Seq[File]]("new-sources")

  // The definition of our "new" configuration.  We're specializing "compile" here
  val NewConfig = config("new") extend(Compile)

  // Here we pull in all of the configurations from the Defaults and then specialize some
  // stuff for our new configuration
  lazy val compileConfigSettings: Seq[Setting[_]] = inConfig(NewConfig)(Defaults.configSettings ++ Seq(
    // BE CAREFUL HERE.  sourceDirectory evaluates to 'src/new' because we're inside the
    // "new" configuration.  If you want to have dirToWatch be 'src/watching' instead of
    // 'src/new/watching' then you need to use '(sourceDirectory in Compile) / "watching"'
    // instead
    dirToWatch <<= sourceDirectory / "watching",

    // The task that will populate our newSources simply looks up all the files in the
    // dirToWatch directory.  You can always prune this with a FileFilter if you'd like.
    newSources <<= dirToWatch map { d => { IO.listFiles(d).toSeq } },

    // Our compile step.  We'll delegate to the 'newCompile' method, but we're going
    // to also depend on the compile in Compile.  This allows us to say
    // new:compile as well as compile.  If we say new:compile then the compile task will
    // run first and must succeed first.
    (compile in NewConfig) <<= newCompile dependsOn (compile in Compile)

    // This last bit is a bit tricky.  We have to add our newSources that are defined in
    // NewConfig to the 'watchSources' that are defined in the global space.  The current
    // block we're in is the 'inConfig(NewConfig)...' section so we need to close that off
    // first.  If we append to 'watchSources' inside our NewConfig then any changes to them
    // won't trigger anything at all.
  )) ++ Seq(watchSources <++= (newSources in NewConfig))

  // Our compile function.  This is pretty dumb and simplistic but the compile logic isn't
  // the goal of this example.  If you need to then you can use the caching mechanism that
  // SBT provides or whatever else.  This is just a toy compile.
  def newCompile = (newSources, target) map { (fs, dir) =>
      fs foreach { file =>
        val targetFile = dir / file.getName
        if (!targetFile.exists || targetFile.lastModified < file.lastModified) {
          println("===> compiled %s".format(file.getName))
          IO.touch(targetFile)
        }
      }
      // Compilers respond with Analysis objects, and we don't really have any to
      // provide, so we're just using an Empty one.
      sbt.inc.Analysis.Empty
    }
}
