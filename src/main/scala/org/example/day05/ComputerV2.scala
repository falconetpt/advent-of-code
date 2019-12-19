package org.example.day05

import org.example.FileReader

import scala.annotation.tailrec

class ComputerV2 {
  private val sumIndex = 1
  private val multiplyIndex = 2
  private val inputIndex = 3
  private val outputIndex = 4
  private val stop = (i: Int) => i == 99

  private val sum = (a: Int, b: Int) => a + b
  private val multiply = (a: Int, b: Int) => a * b

  val opMapping = Map(
    sumIndex -> new Sum(),
    multiplyIndex -> new Multiply(),
    inputIndex -> new Input(),
    outputIndex -> new Output()
  )

  def calculate(file: String): Array[Int] = {
    val string = new FileReader(file).extractResult().next()
    val numberList = string.split(",").map(_.toInt)
    process(numberList)
  }

  def process(numberList: Array[Int]): Array[Int] = {
    @tailrec
    def process(numberList: Array[Int], index: Int): Array[Int] = {
      if(stop(numberList(index)) || index >= numberList.length) {
        numberList
      } else {
        val result = computeOperation(numberList, index)

        numberList(result.position) = result.result

        process(numberList, result.nextIndex)
      }
    }

    process(numberList.clone(), 0)
  }

  private def computeOperation(numberList: Array[Int], index: Int): OperationResult = {
    val inputOperation = numberList(index)
    val operation = opMapping(inputOperation % 100)
    operation.calculate(numberList, index, inputOperation / 100)
  }
}
