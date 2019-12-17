package org.example.day01

import java.io.{File, FileInputStream}

import scala.io.Source

class FileReader(val filePath: String) {
  def extractResult(): List[Int] = {
    val fileStream = Source.fromFile(filePath).getLines
    fileStream.map(_.toInt).toList
  }
}
