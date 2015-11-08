package hk.edu.polyu.ir.groupc.searchengine.model

/**
  * Created by beenotung on 11/8/15.
  */
object Index {
  def getDocumentLength(docId: Int) = ???

  def getFileLength(fileId: Int) = getDocumentLength(getDocId(fileId))

  def getTermFrequent(term: String) = ???

  def getTFIDF(term: String, docId: Int) = ???

  def getDocId(fileId: Int) = ???

  def getFileId(docId: Int) = ???
}
