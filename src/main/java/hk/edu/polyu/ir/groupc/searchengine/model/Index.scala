package hk.edu.polyu.ir.groupc.searchengine.model

import hk.edu.polyu.ir.groupc.searchengine.Debug
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.{DocFileFactory, IDFFactory, TermEntity, TermInfoFactory}

/**
  * Created by beenotung on 11/8/15.
  */
object Index {
  //TODO implementing lazy get
  //TODO avoid lazy

  def getDocumentLength(fileId: Int) = getDocLen(fileId)

  def getDocLen(fileId: Int) = DocFileFactory.getDocFile(fileId).docLen

  @deprecated("slow")
  /* get term frequency by in document (file) */
  def getTF(term: String, fileId: Int): Int = TermInfoFactory.getTermIndex.getTF(term, fileId)

  /* get term frequency by in document (file) */
  def getTF(termEntity: TermEntity, fileId: Int): Int = TermInfoFactory.getTermIndex.getTF(termEntity, fileId)

  @deprecated("slow")
  def getDocumentFrequentByTerm(term: String, fileId: Int): Double = getDF(term, fileId)

  @deprecated("slow")
  def getDF(term: String, fileId: Int): Double = TermInfoFactory.getTermIndex.getDF(term, fileId)

  def getDF(termEntity: TermEntity, fileId: Int): Double = TermInfoFactory.getTermIndex.getDF(termEntity, fileId)

  def getDocumentCount = getDocN

  def getDocN: Double = DocFileFactory.getDocumentCount

  @deprecated("slow")
  def getTFIDF(term: String, fileId: Int) = IDFFactory.getTFIDF(term, fileId)

  def getTFIDF(termEntity: TermEntity, fileId: Int) = IDFFactory.getTFIDF(termEntity, fileId)

  def getIDF(term: String, fileId: Int) = IDFFactory.getIDF(term, fileId)

  def getDocFile(fileId: Int) = DocFileFactory.getDocFile(fileId)

  def hello = {
    Debug.log("Hello")
    "World"
  }
}
