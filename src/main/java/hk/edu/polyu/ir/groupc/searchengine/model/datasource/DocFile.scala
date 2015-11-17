package hk.edu.polyu.ir.groupc.searchengine.model.datasource

import java.io.{File, FileNotFoundException}
import java.util.function.Consumer

import comm.Utils
import comm.exception.RichFileNotFoundException

import scala.collection.mutable.ArrayBuffer

/**
  * Created by beenotung on 11/7/15.
  */
class DocFile(val fileId: Int, val docLen: Int, val docId: String, val path: String) {}

object DocFileFactory {

  private var docFiles: Array[DocFile] = null

  def getDocFile(fileId: Int): DocFile = {
    if (docFiles == null) throw new IllegalStateException("the index has not been loaded")
    docFiles(fileId)
  }

  def getDocumentCount = {
    if (docFiles == null) throw new IllegalStateException("the index has not been loaded")
    docFiles.length
  }

  var maxDocLength: Int = throw new IllegalStateException("the index has not been loaded")
  var avgDocLength: Int = throw new IllegalStateException("the index has not been loaded")

  @throws(classOf[RichFileNotFoundException])
  def load(file: File) = {
    try {
      val arrayBuffer = ArrayBuffer.empty[DocFile]
      Utils.processLines(file, new Consumer[String] {
        override def accept(t: String): Unit = arrayBuffer += createFromString(t)
      })
      docFiles = arrayBuffer.toArray
      maxDocLength = docFiles.map(_.docLen).max
      avgDocLength = docFiles.map(_.docLen).sum / docFiles.length
    } catch {
      case e: FileNotFoundException => throw new RichFileNotFoundException(file)
    }
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
