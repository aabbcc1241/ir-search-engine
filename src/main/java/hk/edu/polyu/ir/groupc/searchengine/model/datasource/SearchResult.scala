package hk.edu.polyu.ir.groupc.searchengine.model.datasource

import hk.edu.polyu.ir.groupc.searchengine.model.query.RetrievalDocument

/**
 * Created by beenotung on 11/6/15.
 */
object SearchResultFactory {
  var runId: String = ???
  var queryId: String = ???

  def setRunId(newRunId: String) = {
    runId = newRunId
  }

  def setQueryId(newQueryId: String) = {
    queryId = newQueryId
  }

  def create(retrievalDocuments: Array[RetrievalDocument]): SearchResult =
    new SearchResult(runId, queryId, retrievalDocuments)
}


class SearchResult(val runId: String, val queryId: String, val retrievalDocuments: Array[RetrievalDocument]) {
  def toStrings: Array[String] = {
    Array.tabulate(retrievalDocuments.length)(i => {
      val doc = retrievalDocuments(i)
      queryId + " " + doc.documentId + " " + (i + 1) + " " + doc.similarityScore + " " + runId
    })
  }
}
