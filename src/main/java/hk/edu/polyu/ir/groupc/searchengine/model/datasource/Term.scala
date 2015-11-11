package hk.edu.polyu.ir.groupc.searchengine.model.datasource

import java.io._
import java.util.function.Consumer

import comm.Utils

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by beenotung on 11/7/15.
  */

/**
  * @usecase ONLY TEMP data holder when parsing from file
  **/
private class RawTermInfo(val term: String, val fileId: Int, val logicalWordPosition: Int)

/**
  * @define key : fileId
  * @define value : positionList
  **/
private class FilePositionMap extends mutable.HashMap[Int, ListBuffer[Int]]

/**
  * @define key : term
  * @define value : FilePositionMap [fileId, positionList]
  **/
private class TermFileMap extends mutable.HashMap[String, FilePositionMap]

class TermEntity(val termStem: String, val filePositionMap: FilePositionMap)

class TermIndex {
  private var underlying = new TermFileMap

  @deprecated("slow")
  def getTF(term: String, fileId: Int): Int = underlying.get(term) match {
    case None => 0
    case Some(filePositionMap) => getTF(new TermEntity(term, filePositionMap), fileId)
  }

  /* get term frequency by in document (file) */
  def getTF(termEntity: TermEntity, fileid: Int): Int = termEntity.filePositionMap.get(fileid) match {
    case None => 0
    case Some(positionList) => positionList.length
  }

  /* get document(file) frequency by term */
  def getDF(termEntity: TermEntity,fileId:Int) = termEntity.filePositionMap.count(x => x._1.==(fileId))

  @deprecated("slow")
  def getDF(term: String,fileId:Int) = underlying.get(term) match {
    case None => 0
    case Some(filePositionMap) => filePositionMap.count(x => x._1 == fileId)
  }

  def addTerm(termInfo: RawTermInfo) = {
    underlying.getOrElseUpdate(termInfo.term, new FilePositionMap)
      .getOrElseUpdate(termInfo.fileId, new ListBuffer[Int])
      .+=:(termInfo.logicalWordPosition)
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

  def writeToFile(filename: String) = {
    //    org.apache.commons.io.FileUtils.deleteQuietly(new File(filename))
    val file = new File(filename)
    val out = new BufferedWriter(new FileWriter(file))
    out.write("# term number of files\n")
    out.write("# fileId position...\n")
    underlying.foreach(termFile => {
      out.write(termFile._1 + " " + termFile._2.size)
      termFile._2.foreach(filePositionMap => {
        out.write("\n" + filePositionMap._1)
        filePositionMap._2.foreach(p => out.write(" " + p))
      }
      )
      out.write("\n")
    }
    )
    out.close()
    //    println(underlying.toString())
    //    output.write(underlying.toString())
  }
}

object TermInfoFactory {
  /* index by term
 * content : HastMap [file id -> List[position] ]
 * example : apple -> (d1->1,2,3),(d2->2,3,4)
 * */
  private var cachedTermIndex: TermIndex = null

  /**
    * @param file : post file
    **/
  def build(file: File) = {
    val termIndex = new TermIndex
    Utils.processLines(file, new Consumer[String] {
      override def accept(t: String): Unit = {
        val post = createFromString(t)
        termIndex.addTerm(post)
      }
    })
    termIndex.shrink()
    cachedTermIndex = termIndex
  }

  private def createFromString(rawString: String): RawTermInfo = {
    val xs = rawString.split(" ")
    new RawTermInfo(xs(0), xs(1).toInt, xs(2).toInt)
  }

  def load(file: File) = ???

  @throws(classOf[IllegalStateException])
  def getTermIndex: TermIndex = {
    if (cachedTermIndex == null) throw new IllegalStateException("index has not been loaded")
    cachedTermIndex
  }
}
