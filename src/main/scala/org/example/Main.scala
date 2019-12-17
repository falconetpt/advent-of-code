package org.example

import org.apache.camel.scala.dsl.builder.RouteBuilderSupport
import org.example.day01.FuelCalculation
import org.example.day02.Computer
import org.example.day03.CrossedWires

object Main extends RouteBuilderSupport {
  private val dayOneFile = "src/main/scala/org/example/day01/input.txt"
  private val dayTwoFile = "src/main/scala/org/example/day02/input.txt"
  private val dayThreeFile = "src/main/scala/org/example/day03/input.txt"

  def main(args: Array[String]) {
    lazy val dayOne = new FuelCalculation().calculate(dayOneFile)
    lazy val dayTwo = new Computer().calculate(dayTwoFile, (0 to 99), 19690720)
    val dayThree = new CrossedWires().process(dayThreeFile)

    println(dayThree)
  }
}

