package org.example.day02

import org.example.FileReader

import scala.annotation.tailrec

class Computer {
  private val sumIndex = 1
  private val multiplyIndex = 2
  private val stop = (i: Int) => i == 99 || (i != sumIndex && i != multiplyIndex)

  private val sum = (a: Int, b: Int) => a + b
  private val multiply = (a: Int, b: Int) => a * b

  val opMapping = Map(
    sumIndex -> sum,
    multiplyIndex -> multiply
  )


  def calculate(file: String, range: Range, expected: Int) = {
    val string = new FileReader(file).extractResult().next()
    val numberList = string.split(",").map(_.toInt)

    range.flatMap(x => range.map(y => {
        val localList = numberList.clone()
        localList(1) = x
        localList(2) = y
        localList
      }))
      .map(process)
      .find(newList => newList(0) == expected).head
  }

  def calculate(file: String) = {
    val string = new FileReader(file).extractResult().next()
    val numberList = string.split(",").map(_.toInt)
    process(numberList)
  }

  def process(numberList: Array[Int]) = {
    @tailrec
    def process(numberList: Array[Int], index: Int): Array[Int] = {
      if(stop(numberList(index)) || index >= numberList.size) {
        numberList
      } else {
        val position = numberList(index + 3)
        val result = computeOperation(numberList, index)

        numberList(position) = result

        process(numberList, index + 4)
      }
    }

    process(numberList.clone(), 0)
  }

  private def computeOperation(numberList: Array[Int], index: Int) = {
    val operation = opMapping(numberList(index))

    val num1 = numberList(index + 1)
    val num2 = numberList(index + 2)

    operation(numberList(num1), numberList(num2))
  }
}
