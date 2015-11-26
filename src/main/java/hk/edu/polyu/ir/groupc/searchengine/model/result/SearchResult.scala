package hk.edu.polyu.ir.groupc.searchengine.model.result

import java.io.{FileWriter, IOException}
import java.util
import java.util.Collections

import comm.FileUtils
import hk.edu.polyu.ir.groupc.searchengine.model.query.Query

import scala.collection.JavaConverters._

/**
  * Created by beenotung on 11/6/15.
  */
object SearchResultFactory {
  var runId: String = "runId_default"

  def setRunId(newRunId: String) = {
    runId = newRunId
  }

  def create(runId: String = runId, query: Query, retrievalDocuments: java.util.List[RetrievalDocument]): SearchResult =
    create(runId, query.queryId, retrievalDocuments)

  def create(runId: String = runId, queryId: String, retrievalDocuments: java.util.List[RetrievalDocument]) =
    new SearchResult(runId, queryId, retrievalDocuments)

  @throws(classOf[IOException])
  def writeToFile(searchResults: java.util.List[SearchResult], filename: String): Unit = writeToFile(searchResults.asScala, filename)


  @throws(classOf[IOException])
  def writeToFile(searchResults: Seq[SearchResult], filename: String): Unit = {
    val lines = searchResults.flatMap(sr => sr.toStrings)
    FileUtils.appendToFile(lines, filename)
  }
}


class SearchResult(val runId: String, val queryId: String, private var retrievalDocuments: java.util.List[RetrievalDocument]) {
  def shrink(numOfRetrievalDocument: Int): Unit = {
    if (numOfRetrievalDocument >= retrievalDocuments.size()) return
    new util.ArrayList[RetrievalDocument](numOfRetrievalDocument)
    Collections.sort[RetrievalDocument](retrievalDocuments)
    val iterator = retrievalDocuments.iterator()
    val results = Array.fill[RetrievalDocument](numOfRetrievalDocument)(iterator.next())
    import scala.collection.JavaConverters._
    retrievalDocuments = results.toList.asJava
  }

  @throws(classOf[IOException])
  def writeToFile(filename: String) = {
    val fw = new FileWriter(filename)
    try {
      fw.write("\n")
      toStrings.foreach(s => fw.write(s + "\n"))
    } catch {
      case e: IOException => fw.close()
        throw e
    }
  }

  def toStrings: Array[String] = {
    Array.tabulate(retrievalDocuments.size())(i => {
      val doc = retrievalDocuments.get(i)
      queryId + " Q0 " + doc.documentId + " " + (i + 1) + " " + doc.similarityScore + " " + runId
    })
  }
}
