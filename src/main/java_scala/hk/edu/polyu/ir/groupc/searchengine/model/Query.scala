package hk.edu.polyu.ir.groupc.searchengine.model

import java.io.File

import scala.io.Source

/**
 * Created by beenotung on 11/6/15.
 */
class Query(val queryId: String, val queryContent: String) {
  override def toString: String = s"$queryId $queryContent"
}

object QueryFactory {
   def createFromFile(file: File): Iterator[Query] = {
    Source.fromFile(file).getLines().map[Query] (createFromString)
  }

  protected def createFromString(rawString: String): Query = {
    val id = rawString.substring(0, 3)
    val content = rawString.substring(4, rawString.length)
    new Query(id.trim, content.trim)
  }

  var cachedQuerys: Iterator[Query] = null
}

