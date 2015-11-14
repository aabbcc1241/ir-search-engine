package hk.edu.polyu.ir.groupc.searchengine.model.datasource

import hk.edu.polyu.ir.groupc.searchengine.model.Index

import scala.collection.mutable

/**
  * Created by beenotung on 11/11/15.
  */
object IDFFactory {
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

  @deprecated("slow")
  def getTFIDF(term: String, fileId: Int): Double =
    term_tfidf_map.getOrElseUpdate(term, new mutable.HashMap[Int, Double]())
      .getOrElseUpdate(fileId, Index.getTF(term, fileId) * getIDF(term, fileId))

  def getIDF(term: String, fileId: Int): Double = term_idf_map.getOrElseUpdate(term, findIDF(Index.getDF(term, fileId), Index.getDocN))

  protected def findIDF(df: Double, docN: Double) = Math.log(docN / df) / Math.log(2)

  def getTFIDF(termEntity: TermEntity, fileId: Int): Double =
    term_tfidf_map.getOrElseUpdate(termEntity.termStem, new mutable.HashMap[Int, Double]())
      .getOrElseUpdate(fileId, Index.getTF(termEntity, fileId) * getIDF(termEntity.termStem, fileId))

}