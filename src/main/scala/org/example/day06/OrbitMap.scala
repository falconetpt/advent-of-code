package org.example.day06

import java.util.Optional

import javax.naming.spi.DirStateFactory.Result
import org.example.FileReader

import scala.annotation.tailrec

class OrbitMap {


  def calculate(file: String) = {
    val string = new FileReader(file).extractResult()
    minumunDistanceYouAndSan(file)
    countOrbits(string.toList)
  }


  def minumunDistanceYouAndSan(file: String) = {
    val string = new FileReader(file).extractResult()
    val orbitMap = generateOrbitMap(string.toList)

    val youParent = orbitMap.keySet.filter(k => orbitMap(k).contains("YOU")).head
    val sanParent = orbitMap.keySet.filter(k => orbitMap(k).contains("SAN")).head
    val commonAncestor = leastCommonAncestor(orbitMap, youParent, sanParent)
    val commonAncestorIndex = calculateIndex(orbitMap, commonAncestor)
    val youIndex = calculateIndex(orbitMap, "YOU")
    val sanIndex = calculateIndex(orbitMap, "SAN")


    sanIndex - commonAncestorIndex + youIndex - commonAncestorIndex - 2
  }

  def calculateIndex(orbitMap: Map[String, List[String]], orig: String): Int = {
    @tailrec
    def calculateIndex(orbit: Map[String, List[String]], orig: String, result: Int): Int = {
      if(!orbitMap.keySet.exists(k => orbitMap(k).contains(orig))) {
        result
      } else {
        val parent = orbitMap.keySet.filter(k => orbitMap(k).contains(orig)).head
        calculateIndex(orbit, parent, result + 1)
      }
    }

    calculateIndex(orbitMap, orig, 0)
  }

//  private def calculateDistanceBetweenTwoNode(orbitMap: Map[String, List[String]], orig: String, dest: String): Int = {
//    @tailrec
//    def calculateDistanceBetweenTwoNode(orbitMap: Map[String, List[String]], orig: String,
//                                        dest: String, result: Int): Int = {
//      if (orig == dest) {
//        result
//      } else {
//        val parent = orbitMap.keySet.find(k => orbitMap(k).contains(orig)).getOrElse(orig)
//        calculateDistanceBetweenTwoNode(orbitMap, parent, dest, result + 1)
//      }
//    }
//
//    calculateDistanceBetweenTwoNode(orbitMap, orig, dest, 0)
//  }

  def leastCommonAncestor(orbitMap: Map[String, List[String]], youParent: String, sanParent: String): String = {
    def leastCommonAncestor(orbitMap: Map[String, List[String]],
                            youParent: String,
                            sanParent: String,
                            visited: Set[String]): String = {
      val alreadySeenNodes = () => List(youParent, sanParent).filter(visited.contains)

      if (alreadySeenNodes().nonEmpty) {
        alreadySeenNodes().head
      } else if(youParent == sanParent) {
        youParent
      } else {
        val newYouParent = orbitMap.keySet.find(k => orbitMap(k).contains(youParent)).getOrElse(youParent)
        val newSanParent = orbitMap.keySet.find(k => orbitMap(k).contains(sanParent)).getOrElse(sanParent)

        leastCommonAncestor(orbitMap, newYouParent, newSanParent, visited ++ Set(youParent, sanParent))
      }
    }

    leastCommonAncestor(orbitMap, youParent, sanParent, Set())
  }

  private def countOrbits(orbitList: List[String]) =  {
    val orbitMap = generateOrbitMap(orbitList)
    val root = orbitMap.keySet
      .filterNot(x => orbitMap.values.flatten.exists(_ == x))
      .head

    calculateOrbits(orbitMap, root)
  }

  private def generateOrbitMap(list: List[String]) : Map[String, List[String]] = {
    list.map(s => s.split("[)]"))
      .groupBy(x => x(0))
      .map((v) => (v._1, v._2.flatten.filterNot(_ == v._1)))
  }

  private def calculateOrbits(orbit: Map[String, List[String]], root: String): Int = {
    def calculateOrbits(orbit: Map[String, List[String]], root: String, result: Int): Int = {
      if(orbit.keySet.contains(root)) {
        var newResult = result
        for (item <- orbit(root)) {
          newResult = newResult + calculateOrbits(orbit, item, result + 1)
        }

        newResult
      } else {
        result
      }
    }

    calculateOrbits(orbit, root, 0)
  }
}

