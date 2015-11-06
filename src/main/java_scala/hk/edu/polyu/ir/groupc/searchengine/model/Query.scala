package hk.edu.polyu.ir.groupc.searchengine.model

import java.io.File

import scala.io.Source

/**
 * Created by beenotung on 11/6/15.
 */
object QueryFactory {
  def createQueryFromString(rawString: String): Query = {
    val id = rawString.substring(0, 3)
    val content = rawString.substring(4, rawString.length)
    new Query(id, content)
  }

  def createQuerysFromFile(file: File): Iterator[Query] = {
    Source.fromFile(file).getLines().map[Query] (createQueryFromString)
  }
}

class Query(val id: String, val conten: String) {

}
