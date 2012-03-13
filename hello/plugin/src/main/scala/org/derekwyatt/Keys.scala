package org.derekwyatt

import sbt._

trait Keys {
  val messageToPrint = SettingKey[String]("hello-message", "The message to print to stdout.")
  val hello = TaskKey[Unit]("hello", "Prints the message to stdout.")
}

object Keys extends Keys
