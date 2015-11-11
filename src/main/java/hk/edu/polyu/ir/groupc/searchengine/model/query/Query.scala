package hk.edu.polyu.ir.groupc.searchengine.model.query

import java.io.File

import scala.io.Source

/**
  * Created by beenotung on 11/6/15.
  */
class Query(val queryId: String, val queryContent: String) {
  override def toString: String = s"$queryId $queryContent"
}

object QueryFactory {
   def loadFromFile(file: File) = {
    queries = Source.fromFile(file).getLines().map[Query](createFromString)
  }

  private def createFromString(rawString: String): Query = {
    val id = rawString.substring(0, 3)
    val content = rawString.substring(4, rawString.length)
    new Query(id.trim, content.trim)
  }

  @throws(classOf[IllegalStateException])
  private var queries: Iterator[Query] = null

  def getQueries: Iterator[Query] = {
    if (queries == null) throw new IllegalStateException("query has not been loaded")
    queries
  }
}

