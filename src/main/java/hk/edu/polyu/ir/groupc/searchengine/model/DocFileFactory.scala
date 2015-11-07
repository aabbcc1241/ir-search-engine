package hk.edu.polyu.ir.groupc.searchengine.model

import java.io.File
import java.util.function.Consumer

import hk.edu.polyu.ir.groupc.Utils

import scala.collection.mutable.ArrayBuffer

/**
  * Created by beenotung on 11/7/15.
  */
class DocFile(val fileId: Int,val docLen: Int,val docId: String,val path: String) {}

object DocFileFactory {

  private var index: Array[DocFile] = null

  def getDocFile(fileId: Int): DocFile = {
    if (index == null) throw new IllegalStateException("the index has not been loaded")
    index(fileId)
  }

  def getDocN = {
    if (index == null) throw new IllegalStateException("the index has not been loaded")
    index.length
  }

  def loadFromFile(file: File) = {
    val arrayBuffer = ArrayBuffer.empty[DocFile]
    Utils.processLines(file, new Consumer[String] {
      override def accept(t: String): Unit = arrayBuffer += createFromString(t)
    })
    index = arrayBuffer.toArray
  }

  protected def createFromString(rawString: String): DocFile = {
    val xs = rawString.split(" ")
    val fileId = xs(0)
    val docLen = xs(1)
    val docId = xs(3)
    val path = xs(4)
    new DocFile(fileId.toInt, docLen.toInt, docId, path)
  }
}
