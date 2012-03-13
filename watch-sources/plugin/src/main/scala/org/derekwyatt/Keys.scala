package org.derekwyatt

import sbt._

trait Keys {
  val dirToWatch = SettingKey[File]("dir-to-watch", "The directory to watch for changing files.")
}

object Keys extends Keys
