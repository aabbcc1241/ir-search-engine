package hk.edu.polyu.ir.groupc.searchengine.model.datasource

import hk.edu.polyu.ir.groupc.searchengine.model.Index

import scala.collection.mutable

/**
  * Created by beenotung on 11/11/15.
  */
object IDFFactory {
  //TODO avoid lazy init
  /**
    * @define key term
    * @define value idf
    **/
  private val term_idf_map = new mutable.HashMap[String, Double]()
  /**
    * @define key term
    * @define key_ fileId
    * @define value tfidf
    **/
  private val term_tfidf_map = new mutable.HashMap[String, mutable.HashMap[Int, Double]]()
  var maxIDF: Double = -1
  var avgIDF: Double = -1

  @Deprecated
  @deprecated("slow")
  def getTFIDF(term: String, fileId: Int): Double =
    term_tfidf_map.getOrElseUpdate(term, new mutable.HashMap[Int, Double]())
      .getOrElseUpdate(fileId, Index.getTF(term, fileId) * getIDF(term))

  def getTFIDF(termEntity: TermEntity, fileId: Int): Double =
    term_tfidf_map.getOrElseUpdate(termEntity.termStem, new mutable.HashMap[Int, Double]())
      .getOrElseUpdate(fileId, Index.getTF(termEntity, fileId) * getIDF(termEntity.termStem))

  @Deprecated
  @deprecated("slow")
  def getIDF(term: String): Double = term_idf_map.getOrElseUpdate(term, findIDF(term))

  @Deprecated
  @deprecated("slow")
  protected def findIDF(term: String): Double = {
    findIDF(Index.getDocumentCount, TermIndexFactory.getTermIndex.getDF(term))
  }

  /*return cached idf, update if not cached*/
  def getIDF(term: TermEntity): Double = term_idf_map.getOrElseUpdate(term.termStem, findIDF(Index.getDocumentCount, term.filePositionMap.size))

  /*calculate idf*/
  def findIDF(docN: Int, documentFrequency: Int) = Math.log(docN / (1d + documentFrequency))

  def storeIDF(term: String, idf: Double) = term_idf_map.put(term, idf)

  def updateStatis() = {
    maxIDF = term_idf_map.values.max
    avgIDF = term_idf_map.values.sum / term_idf_map.size
  }
}