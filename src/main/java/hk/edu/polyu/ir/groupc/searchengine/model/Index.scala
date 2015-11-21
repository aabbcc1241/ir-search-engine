package hk.edu.polyu.ir.groupc.searchengine.model

import hk.edu.polyu.ir.groupc.searchengine.Debug
import hk.edu.polyu.ir.groupc.searchengine.model.datasource._

/**
  * Created by beenotung on 11/8/15.
  */
object Index {
  //TODO avoid lazy

  def getFilePositionMap(term: String) = TermIndexFactory.getTermIndex.getFilePositionMap(term)

  def getDocumentLength(fileId: Int) = DocumentIndexFactory.getDocumentIndex.getDocumentLength(fileId)

  @Deprecated
  @deprecated("slow")
  /* get term frequency by in document (file) */
  def getTF(term: String, fileId: Int): Int = TermIndexFactory.getTermIndex.getTF(term, fileId)

  /* get term frequency by in document (file) */
  def getTF(termEntity: TermEntity, fileId: Int): Int = TermIndexFactory.getTermIndex.getTF(termEntity, fileId)


  def getDocumentCount = DocFileFactory.getDocumentCount

  @Deprecated
  @deprecated("slow")
  def getTFIDF(term: String, fileId: Int) = IDFFactory.getTFIDF(term, fileId)

  def getTFIDF(termEntity: TermEntity, fileId: Int) = IDFFactory.getTFIDF(termEntity, fileId)

  @Deprecated
  @deprecated("slow")
  def getIDF(term: String) = IDFFactory.getIDF(term)

  def getIDF(term: TermEntity) = IDFFactory.getIDF(term)

  def getDocFile(fileId: Int) = DocFileFactory.getDocFile(fileId)

  def getTermEntity(termStem: String) = {
    TermIndexFactory.getTermIndex.getTermEntity(termStem)
  }

  @throws(classOf[IllegalStateException])
  def averageDocumentLength = DocumentIndexFactory.getDocumentIndex.docLenAvg

  @throws(classOf[IllegalStateException])
  def medianDocumentLength = DocumentIndexFactory.getDocumentIndex.docLenMedian

  @throws(classOf[IllegalStateException])
  def maxIDF = IDFFactory.maxIDF

  @throws(classOf[IllegalStateException])
  def maxTermFrequency(fileId: Int) = DocumentIndexFactory.getDocumentIndex.maxTF(fileId)

  @throws(classOf[IllegalStateException])
  def averageIDF = IDFFactory.avgIDF

  def hello = {
    Debug.log("Hello")
    "World"
  }
}
