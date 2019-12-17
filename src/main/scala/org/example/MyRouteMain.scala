package org.example

import org.apache.camel.main.Main
import org.apache.camel.scala.dsl.builder.RouteBuilderSupport
import org.example.day01.FuelCalculation

/**
 * A Main to run Camel with MyRouteBuilder
 */
object MyRouteMain extends RouteBuilderSupport {
  private val filePath = "src/main/scala/org/example/day01/input.txt"

  def main(args: Array[String]) {
    val calculus = new FuelCalculation().calculate(filePath)
    println(calculus)
  }
}

