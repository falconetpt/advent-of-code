package org.example.day04

import scala.annotation.tailrec

class SecureContainer {
  def validPassword(range: Range): Int = {

    range
      .filter(crescentNumbers)
      .count(onlyOneConsecutiveNumberExists)
  }

  private def crescentNumbers(value: Int): Boolean = {
    val strRepresentation = value.toString

    (1 until strRepresentation.size)
      .find(i => strRepresentation(i-1).toInt > strRepresentation(i).toInt)
      .map(_ => false)
      .getOrElse(true)
  }

  private def onlyOneConsecutiveNumberExists(value: Int): Boolean = {
    @tailrec
    def validate(value: String, before: String,
                 runningValue: String, result: Boolean): Boolean = {
      if(value.isEmpty || result) {
        result || runningValue.size == 2
      } else {
        val (current, next) = value.splitAt(1)

        if(current != before) {
          validate(next, current, current, runningValue.size == 2)
        } else {
          validate(next, current, runningValue + current, result)
        }

      }
    }

    validate(value.toString, "", "",false)
  }
}
