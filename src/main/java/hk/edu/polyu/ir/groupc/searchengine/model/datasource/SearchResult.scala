package hk.edu.polyu.ir.groupc.searchengine.model.datasource

import java.io.{FileWriter, IOException}

import comm.FileUtils
import hk.edu.polyu.ir.groupc.searchengine.model.query.{Query, RetrievalDocument}

import scala.collection.JavaConverters._

/**
  * Created by beenotung on 11/6/15.
  */
object SearchResultFactory {
  var runId: String = "runId_default"

  def setRunId(newRunId: String) = {
    runId = newRunId
  }

  def create(query: Query, retrievalDocuments: java.util.List[RetrievalDocument]): SearchResult =
    create(query.queryId, retrievalDocuments)

  def create(queryId: String, retrievalDocuments: java.util.List[RetrievalDocument]): SearchResult =
    new SearchResult(runId, queryId, retrievalDocuments)

  @throws(classOf[IOException])
  def writeToFile(searchResults: java.util.List[SearchResult], filename: String): Unit = writeToFile(searchResults.asScala, filename)


  @throws(classOf[IOException])
  def writeToFile(searchResults: Seq[SearchResult], filename: String): Unit = {
    val lines = searchResults.flatMap(sr => sr.toStrings)
    FileUtils.appendToFile(lines, filename)
  }
}


class SearchResult(val runId: String, val queryId: String, val retrievalDocuments: java.util.List[RetrievalDocument]) {
  def toStrings: Array[String] = {
    Array.tabulate(retrievalDocuments.size())(i => {
      val doc = retrievalDocuments.get(i)
      queryId + " " + doc.documentId + " " + (i + 1) + " " + doc.similarityScore + " " + runId
    })
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
}
