package org.example

import scala.io.Source

class FileReader(val filePath: String) {
  def extractResult(): Iterator[String] = {
    Source.fromFile(filePath).getLines
  }
}
