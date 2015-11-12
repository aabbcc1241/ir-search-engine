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

  def create(retrievalDocuments: java.util.List[RetrievalDocument]): SearchResult =
    new SearchResult(runId, queryId, retrievalDocuments)

  def create(queryId: String, retrievalDocuments: java.util.List[RetrievalDocument]): SearchResult =
    new SearchResult(runId, queryId, retrievalDocuments)
}


class SearchResult(val runId: String, val queryId: String, val retrievalDocuments: java.util.List[RetrievalDocument]) {
  def toStrings: Array[String] = {
    Array.tabulate(retrievalDocuments.size())(i => {
      val doc = retrievalDocuments.get(i)
      queryId + " " + doc.documentId + " " + (i + 1) + " " + doc.similarityScore + " " + runId
    })
  }
}
