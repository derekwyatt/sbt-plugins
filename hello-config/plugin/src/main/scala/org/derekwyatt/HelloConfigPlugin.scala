package org.derekwyatt

import sbt._

object HelloConfigPlugin extends Plugin {
  import Keys._
  val HelloConfig = config("hello")
  def helloConfigSettings = inConfig(HelloConfig)(Seq(
    messageToPrint := "Hello with Config",
    hello <<= messageToPrint map { (msg) => println(msg) }
  ))
}
