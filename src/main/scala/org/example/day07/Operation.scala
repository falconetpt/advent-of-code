package org.example.day07

import scala.annotation.tailrec

trait Operation {
  def calculate(numberList: Array[Int], startIndex: Int, mode: Int, param: List[Int]): OperationResult
}

class OperationResult(val result: Int, val position: Int, val nextIndex: Int, val param: List[Int])

object OperationModeExtract {
  def extractModes(number: Int, modeNbr: Int): List[Int] = {
    @tailrec
    def extractModes(number: Int, modeNbr: Int, result: List[Int]): List[Int] = {
      if(result.size == modeNbr) {
        result
      } else {
        extractModes(number/10, modeNbr, result ++ List(number % 10))
      }
    }

    extractModes(number, modeNbr, List())
  }

  def applyMode(list: Array[Int], index: Int, mode: Int): Int = {
    val immediateMode = 0
    val positionMode = 1
    val position = list(index)

    if(mode == positionMode) {
      position
    } else {
      list(position)
    }
  }
}

class Sum extends Operation {
  private val sum = (a: Int, b: Int) => a + b

  override def calculate(numberList: Array[Int], startIndex: Int, mode: Int, param: List[Int]): OperationResult = {
    val modes = OperationModeExtract.extractModes(mode, 3)

    val num1 = OperationModeExtract.applyMode(numberList, startIndex + 1, modes(0))
    val num2 = OperationModeExtract.applyMode(numberList, startIndex + 2, modes(1))
    val position = numberList(startIndex + 3)

    new OperationResult(sum(num1, num2), position, startIndex + 4, param)
  }
}

class Multiply extends Operation {
  private val multiply = (a: Int, b: Int) => a * b

  override def calculate(numberList: Array[Int], startIndex: Int, mode: Int, param: List[Int]): OperationResult = {
    val modes = OperationModeExtract.extractModes(mode, 3)

    val num1 = OperationModeExtract.applyMode(numberList, startIndex + 1, modes(0))
    val num2 = OperationModeExtract.applyMode(numberList, startIndex + 2, modes(1))
    val position = numberList(startIndex + 3)

    new OperationResult(multiply(num1, num2), position, startIndex + 4, param)
  }
}

class Input extends Operation {
  override def calculate(numberList: Array[Int], startIndex: Int, mode: Int, param: List[Int]): OperationResult = {
    val position = numberList(startIndex + 1)
    val (input, nextParam) = param.splitAt(1)

    new OperationResult(input.head, position, startIndex + 2, nextParam)
  }
}

class Output extends Operation {
  override def calculate(numberList: Array[Int], startIndex: Int, mode: Int, param: List[Int]): OperationResult = {
    val modes = OperationModeExtract.extractModes(mode, 3)
    val num = OperationModeExtract.applyMode(numberList, startIndex + 1, modes.head)

    new OperationResult(num, startIndex + 1, startIndex + 2, param)
  }
}

class JumpIfTrue extends Operation {
  override def calculate(numberList: Array[Int], startIndex: Int, mode: Int, param: List[Int]): OperationResult = {
    val modes = OperationModeExtract.extractModes(mode, 3)
    val num1 = OperationModeExtract.applyMode(numberList, startIndex + 1, modes(0))
    val num2 = OperationModeExtract.applyMode(numberList, startIndex + 2, modes(1))
    val postion = if(num1 != 0) num2 else startIndex + 3

    new OperationResult(numberList(startIndex), startIndex, postion, param)
  }
}

class JumpIfFalse extends Operation {
  override def calculate(numberList: Array[Int], startIndex: Int, mode: Int, param: List[Int]): OperationResult = {
    val modes = OperationModeExtract.extractModes(mode, 3)
    val num1 = OperationModeExtract.applyMode(numberList, startIndex + 1, modes(0))
    val num2 = OperationModeExtract.applyMode(numberList, startIndex + 2, modes(1))
    val postion = if(num1 == 0) num2 else startIndex + 3

    new OperationResult(numberList(startIndex), startIndex, postion, param)
  }
}

class LessThan extends Operation {
  override def calculate(numberList: Array[Int], startIndex: Int, mode: Int, param: List[Int]): OperationResult = {
    val modes = OperationModeExtract.extractModes(mode, 3)
    val num1 = OperationModeExtract.applyMode(numberList, startIndex + 1, modes(0))
    val num2 = OperationModeExtract.applyMode(numberList, startIndex + 2, modes(1))
    val position = numberList(startIndex + 3)

    val value = if (num1 < num2) 1 else 0

    new OperationResult(value, position, startIndex + 4, param)
  }
}

class Equals extends Operation {
  override def calculate(numberList: Array[Int], startIndex: Int, mode: Int, param: List[Int]): OperationResult = {
    val modes = OperationModeExtract.extractModes(mode, 3)
    val num1 = OperationModeExtract.applyMode(numberList, startIndex + 1, modes(0))
    val num2 = OperationModeExtract.applyMode(numberList, startIndex + 2, modes(1))
    val position = numberList(startIndex + 3)

    val value = if (num1 == num2) 1 else 0

    new OperationResult(value, position, startIndex + 4, param)
  }
}
