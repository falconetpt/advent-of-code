package org.example.day07

import org.example.FileReader

import scala.annotation.tailrec

class ComputerV3 {
  private val sumIndex = 1
  private val multiplyIndex = 2
  private val inputIndex = 3
  private val outputIndex = 4
  private val jumpIfTrueIndex = 5
  private val jumpIfFalseIndex = 6
  private val lessThanIndex = 7
  private val equalsIndex = 8

  private val stop = (i: Int) => i % 100 == 4

  private val sum = (a: Int, b: Int) => a + b
  private val multiply = (a: Int, b: Int) => a * b

  val opMapping = Map(
    sumIndex -> new Sum(),
    multiplyIndex -> new Multiply(),
    inputIndex -> new Input(),
    outputIndex -> new Output(),
    jumpIfTrueIndex -> new JumpIfTrue(),
    jumpIfFalseIndex -> new JumpIfFalse(),
    lessThanIndex -> new LessThan(),
    equalsIndex -> new Equals()
  )

  def calculate(file: String): Int = {
    val string = new FileReader(file).extractResult().next()
    val numberList = string.split(",").map(_.toInt)
    calculate(numberList)
  }

  private def calculate(numberList: Array[Int]): Int = {
    val list = generatePermutations()

    list.par.map(calculateWithInput(numberList, _)).max
  }

  private def calculateWithInput(numberList: Array[Int], input: List[Int]): Int = {
    @tailrec
    def calculateWithInput(numberList: Array[Int], input: List[Int], output: Int, result: Int): Int = {
      if(input.isEmpty) {
        result
      } else {
        val (firstInput, nextInput) = input.splitAt(1)
        val newOutput = process(numberList, List(firstInput.head, output))
        calculateWithInput(numberList, nextInput, newOutput, newOutput)
      }
    }

    calculateWithInput(numberList, input, 0, 0)
  }

  def process(numberList: Array[Int], param: List[Int]): Int = {
    @tailrec
    def process(numberList: Array[Int], index: Int, param: List[Int]): Int = {
      if(stop(numberList(index))) {
        computeOperation(numberList, index, param).result
      } else {
        val result = computeOperation(numberList, index, param)

        numberList(result.position) = result.result

        process(numberList, result.nextIndex, result.param)
      }
    }

    process(numberList.clone(), 0, param)
  }

  private def computeOperation(numberList: Array[Int], index: Int, param: List[Int]): OperationResult = {
    val inputOperation = numberList(index)
    val operation = opMapping(inputOperation % 100)
    operation.calculate(numberList, index, inputOperation / 100, param)
  }

  private def generatePermutations(): List[List[Int]] = {
    (0 to 4).toList.permutations.toList
  }
}
