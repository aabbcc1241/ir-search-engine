package hk.edu.polyu.ir.groupc.searchengine.model

import java.io.File

import scala.io.Source

/**
 * Created by beenotung on 11/6/15.
 */
object QueryFactory {
  def createFromString(rawString: String): Query = {
    val id = rawString.substring(0, 3)
    val content = rawString.substring(4, rawString.length)
    new Query(id.trim, content.trim)
  }

  def createFromFile(file: File): Iterator[Query] = {
    Source.fromFile(file).getLines().map[Query] (createFromString)
  }
}

class Query(val id: String, val content: String) {
  override def toString: String = {
    /*    Map(
    //      "raw" -> super.toString,
          "id" -> id,
          "content" -> content
        ).toString()*/
    s"$id $content"
  }
}
