package hk.edu.polyu.ir.groupc.searchengine.model

import java.io.File
import java.util.function.Consumer

import hk.edu.polyu.ir.groupc.Utils

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by beenotung on 11/7/15.
  */
class Post(val term: String, val fileId: Int, val logicalWordPosition: Int) {

}

abstract class Index() {

  def getDocumentLength(docId: Int) = ???

  def getTermFrequent(term: String) = ???

  def getTFIDF(term: String, docId: Int) = ???

  def getDocId(fileId: Int) = ???

  def getFileId(docId: Int) = ???
}

/**
  * @define key : fileId
  * @define value : positionList
  **/
private class FilePositionMap extends mutable.HashMap[Int, ListBuffer[Int]] {}

private class TermFileMap extends mutable.HashMap[String, FilePositionMap] {}

class TermIndex extends Index {
  private var underlying = new TermFileMap

  def addPost(post: Post) = {
    underlying.getOrElseUpdate(post.term, new FilePositionMap)
      .getOrElseUpdate(post.fileId, new ListBuffer[Int])
      .+=:(post.logicalWordPosition)
  }

  def shrink() = {
    val newInstance = new TermFileMap
    underlying.foreach(termFile => {
      val filePositionMap: FilePositionMap = new FilePositionMap
      termFile._2.foreach(filePosition => {
        filePositionMap.put(filePosition._1, filePosition._2.distinct.sortWith(_ < _))
      })
      newInstance.put(termFile._1, filePositionMap)
    })
    underlying = newInstance
  }

  def toFile(filename: String) = {
    FileU
  }
}

object PostFactory {
  /* index by term
 * content : HastMap [file id -> List[position] ]
 * example : apple -> (d1->1,2,3),(d2->2,3,4)
 * */
  private var cachedTermIndex: TermIndex = null

  /**
    * @param file : post file
    **/
  def buildTermIndex(file: File) = {
    val termIndex = new TermIndex
    Utils.processLines(file, new Consumer[String] {
      override def accept(t: String): Unit = {
        val post = createFromString(t)
        termIndex.addPost(post)
      }
    })
    termIndex.shrink()
    cachedTermIndex = termIndex
  }

  private def createFromString(rawString: String): Post = {
    val xs = rawString.split(" ")
    new Post(xs(0), xs(1).toInt, xs(2).toInt)
  }

  def loadIndex(file: File) = ???

  @throws(classOf[IllegalStateException])
  def getTermIndex: TermIndex = {
    if (cachedTermIndex == null) throw new IllegalStateException("index has not been loaded")
    cachedTermIndex
  }
}
