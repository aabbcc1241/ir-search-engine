package hk.edu.polyu.ir.groupc.searchengine.model.query

import java.io.{File, FileNotFoundException}

import comm.exception.RichFileNotFoundException
import hk.edu.polyu.ir.groupc.searchengine.model.query.QueryFactory.ExpandedQuery

import scala.io.Source

/**
  * Created by beenotung on 11/6/15.
  */
class Query(val queryId: String, val queryContent: String) {
  override def toString: String = s"$queryId $queryContent"

  lazy val expandedQuery: ExpandedQuery = QueryFactory.expand(this)
}

class ExpandedTerm(val term: String, val weight: Double)


object QueryFactory {
  type ExpandedQuery = Array[ExpandedTerm]

  def expand(query: Query): ExpandedQuery = {
    query.queryContent.split(" ").map(s => new ExpandedTerm(s, 1))
  }

  @throws(classOf[RichFileNotFoundException])
  def loadFromFile(file: File) = {
    try {
      queries = Source.fromFile(file).getLines().map[Query](createFromString)
    } catch {
      case e: FileNotFoundException => throw new RichFileNotFoundException(file)
    }
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

