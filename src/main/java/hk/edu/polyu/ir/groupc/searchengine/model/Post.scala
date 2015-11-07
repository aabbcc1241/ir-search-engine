package hk.edu.polyu.ir.groupc.searchengine.model

import java.io.File
import java.util.function.Function

import hk.edu.polyu.ir.groupc.Utils

/**
  * Created by beenotung on 11/7/15.
  */
class Post(val term: String, val fileId: Int, val logicalWordPosition: Int) {

}

class Index() {

  def getDocumentLength(docId: Int) = ???

  def getTermFrequent(term: String) = ???

  def getTFIDF(term: String, docId: Int) = ???

  def getDocId(fileId: Int) = ???

  def getFileId(docId: Int) = ???
}

object PostFactory {
  protected var index: Index = null

  def buildIndex(file: File) = {
    //    posts = PostFactory_.loadFromFile(file)
    val postStream = Utils.getLinesConverted[Post](file, new Function[String, Post] {
      override def apply(rawString: String): Post = createFromString(rawString)
    })
    index = null
  }

  @throws(classOf[IllegalStateException])
  def getIndex: Index = {
    if (index == null) throw new IllegalStateException("index has not been loaded")
    index
  }

  def createFromString(rawString: String): Post = {
    val xs = rawString.split(" ")
    new Post(xs(0), xs(1).toInt, xs(2).toInt)
  }
}
