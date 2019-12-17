package org.example.day01

import org.example.FileReader

import scala.annotation.tailrec

class FuelCalculation {
  def calculate(file: String): Int = {
    val list = new FileReader(file).extractResult().map(_.toInt).toList
    list.map(calculate).sum
  }

  private def calculate(mass: Int): Int = {
    @tailrec
    def calculate(mass: Int, result: Int): Int = {
      val calc = mass / 3 - 2

      if(calc <= 0) {
        result
      } else {
        calculate(calc, result + calc)
      }
    }

    calculate(mass, 0)
  }
}
