package hk.edu.polyu.ir.groupc.searchengine.model

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

  def create(simpleSearchResults: Iterator[SimpleSearchResult]): Iterator[SearchResult] =
    simpleSearchResults.zipWithIndex.map[SearchResult](simple=>
      create(simple._1.docId, simple._2 +1, simple._1.similarityScore)
    )

  def create(docId: String, rankNumber: Int, similarityScore: Double): SearchResult = {
    new SearchResult(docId, queryId, rankNumber, similarityScore, runId)
  }

}

class SimpleSearchResult(val docId: String, val similarityScore: Double)

class SearchResult(val docId: String, val queryId: String, val rankNumber: Int, val similarityScore: Double, val runId: String) {
  override def toString: String = {
    s"$queryId Q0 $docId $rankNumber $similarityScore $runId"
  }
}
