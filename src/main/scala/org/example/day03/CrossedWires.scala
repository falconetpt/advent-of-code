package org.example.day03

import org.example.FileReader

import scala.annotation.tailrec

class CrossedWires {
  private val right = (moves: Int) => (0 until moves).map(_ => (1, 0))
  private val left = (moves: Int) => (0 until moves).map(_ => (-1, 0))
  private val up = (moves: Int) => (0 until moves).map(_ => (0, 1))
  private val down = (moves: Int) => (0 until moves).map(_ => (0, -1))

  private val moveMap = Map(
    "R" -> right,
    "L" -> left,
    "U" -> up,
    "D" -> down
  )

  def process(filename: String) : (Int, Int) = {
    val extractResult = new FileReader(filename).extractResult()
    val moveset1 = extractResult.next().split(",").flatMap(convertToMoveSet)
    val moveset2 = extractResult.next().split(",").flatMap(convertToMoveSet)

    val locations1 = calculateSets(moveset1)
    val locations2 = calculateSets(moveset2)

    val minimumDistanceIntersect = locations1.keySet.intersect(locations2.keySet)
      .map(x => Math.abs(x._1) + Math.abs(x._2))
      .min

    val minimumStepIntersect = locations1.keySet.intersect(locations2.keySet)
      .map(key => locations1(key) + locations2(key))
      .min

    (minimumDistanceIntersect, minimumStepIntersect)
  }


  private def calculateSets(array: Array[(Int, Int)]): Map[(Int, Int), Int] = {
    @tailrec
    def calculateSets(array: Array[(Int, Int)],
                      index: Int,
                      pointBefore: (Int, Int),
                      result: Map[(Int, Int), Int]): Map[(Int, Int), Int] = {
      if(index >= array.size) {
        result
      } else {
        val (xTranslation, yTransalation) = array(index)
        val (xBefore, yBefore) = pointBefore
        val newPoint = (xBefore + xTranslation, yBefore + yTransalation)

        calculateSets(array, index + 1, newPoint, result ++ Map(newPoint -> (index + 1)))
      }
    }

    calculateSets(array, 0, (0, 0), Map())
  }

  private def convertToMoveSet(x: String): IndexedSeq[(Int, Int)] = {
    val (moveset, moves) = x.splitAt(1)
    moveMap(moveset)(moves.toInt)
  }
}
