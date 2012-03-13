package org.derekwyatt

import sbt._

object HelloPlugin extends Plugin {
  import Keys._
  val HelloConfig = config("hello")
  def helloSettings = Seq(
    messageToPrint := "Hello",
    hello <<= messageToPrint map { (msg) => println(msg) }
  )
}
