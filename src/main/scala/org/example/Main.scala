package org.example

import org.apache.camel.scala.dsl.builder.RouteBuilderSupport
import org.example.day01.FuelCalculation
import org.example.day02.Computer
import org.example.day03.CrossedWires
import org.example.day04.SecureContainer
import org.example.day05.ComputerV2
import org.example.day06.OrbitMap
import org.example.day07.ComputerV3

import scala.util.Try



object Main extends RouteBuilderSupport {
  private val dayOneFile = "src/main/scala/org/example/day01/input.txt"
  private val dayTwoFile = "src/main/scala/org/example/day02/input.txt"
  private val dayThreeFile = "src/main/scala/org/example/day03/input.txt"
  private val dayFiveFile = "src/main/scala/org/example/day05/input.txt"
  private val daySixFile = "src/main/scala/org/example/day06/input.txt"
  private val daySevenFile = "src/main/scala/org/example/day07/input.txt"

  def main(args: Array[String]) {
    lazy val dayOne = new FuelCalculation().calculate(dayOneFile)
    lazy val dayTwo = new Computer().calculate(dayTwoFile)
    lazy val dayThree = new CrossedWires().process(dayThreeFile)
    lazy val dayFour = new SecureContainer().validPassword((147981 to 691423))
    lazy val dayFive = new ComputerV2().calculate(dayFiveFile)
    lazy val daySix = new OrbitMap().minumunDistanceYouAndSan(daySixFile)
    lazy val daySeven = new ComputerV3().calculate(daySevenFile)


    println(daySeven)
  }
}

