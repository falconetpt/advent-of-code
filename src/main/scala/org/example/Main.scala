package org.example

import org.apache.camel.scala.dsl.builder.RouteBuilderSupport
import org.example.day01.FuelCalculation
import org.example.day02.Computer

object Main extends RouteBuilderSupport {
  private val dayOneFile = "src/main/scala/org/example/day01/input.txt"
  private val dayTwoFile = "src/main/scala/org/example/day02/input.txt"

  def main(args: Array[String]) {
    lazy val dayOne = new FuelCalculation().calculate(dayOneFile)
    val dayTwo = new Computer().calculate(dayTwoFile, (0 to 99), 19690720)
    val result = dayTwo.foldRight("") ((a, b) => s"$a, $b")
    println(result)
  }
}

