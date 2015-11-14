package hk.edu.polyu.ir.groupc.searchengine.model.query

import java.io.{File, FileNotFoundException}

import comm.exception.RichFileNotFoundException
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.TermEntity
import hk.edu.polyu.ir.groupc.searchengine.model.query.QueryFactory.ExpandedQuery

import scala.io.Source

/**
  * Created by beenotung on 11/6/15.
  */
class Query(val queryId: String, val rawQueryContent: String) {
  override def toString: String = s"$queryId $rawQueryContent"
lazy val queryTerms:Array[TermEntity]=QueryFactory.extract(this)
  lazy val expandedQuery: Array[ExpandedTerm] = QueryFactory.expand(this)
}

class ExpandedTerm(val term: String, val weight: Double)

class QueryDescribtion(val relevant:Boolean,val primarily:Boolean,val primarilyMentioned:Boolean)

object QueryFactory {

  def stem(string: String): String = {
    val stemmer = new Stemmer()
    stemmer.add(string.toCharArray, string.length)
    stemmer.stem()
    stemmer.toString.toLowerCase
  }
def extract
  def expand(query: Query): Array[ExpandedTerm] = {
    query.rawQueryContent.split(" ").map(s => new ExpandedTerm(stem(s), 1))
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

