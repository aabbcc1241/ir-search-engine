package hk.edu.polyu.ir.groupc.searchengine.model.datasource

import java.io.{BufferedWriter, File, FileNotFoundException, FileWriter}
import java.util.function.Consumer

import comm.Utils
import comm.exception.{InvalidFileFormatException, RichFileNotFoundException}
import hk.edu.polyu.ir.groupc.searchengine.Debug
import hk.edu.polyu.ir.groupc.searchengine.Debug.logp
import hk.edu.polyu.ir.groupc.searchengine.model.Index
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.DocumentIndexFactory.{DocTermPositionMap, TermPositionMap}
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.TermIndexFactory.TermFileMap

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Created by beenotung on 11/15/15.
  */
class DocumentIndex(initMap: DocTermPositionMap = new DocTermPositionMap) {
  val file_maxTF = mutable.HashMap.empty[Int, Int]
  val file_docLen = mutable.HashMap.empty[Int, Double]
  var docLenAvg = 0d
  var docLenMedian = 0d
  private var underlying = initMap

  @deprecated("used during test only")
  def reset() = underlying = null

  def writeToFile(filename: String) = {
    val out = new BufferedWriter(new FileWriter(filename))
    out.write("# fileId, number of lines\n")
    out.write("# term, position...\n")
    underlying.foreach(docInfo => {
      out.write(docInfo._1 + " " + docInfo._2.size)
      docInfo._2.toStream.foreach(termPosition => {
        out.write("\n" + termPosition._1)
        termPosition._2.foreach(p => out.write(" " + p))
      })
      out.write("\n")
    })
    out.close()
  }

  def getDocumentLength(fileId: Int) = {
    file_docLen.getOrElseUpdate(fileId, findDocumentLength(fileId))
  }

  protected def findDocumentLength(fileId: Int): Double = {
    underlying.get(fileId) match {
      /*document not found*/
      case None => 0d
      /*document found*/
      case Some(termPositionMap: TermPositionMap) => findDocumentLength(termPositionMap)
    }
  }

  protected def findDocumentLength(termPositionMap: TermPositionMap): Double = {
    var sum = 0d
    termPositionMap.foreach(termPosition => {
      val idf = Index.getIDF(termPosition._1)
      val tf = termPosition._2.length
      sum += Math.pow(tf * idf, 2)
    })
    Math.sqrt(sum)
  }

  def updateStatis() = {
    /*update all document length*/
    underlying.foreach(p => {
      file_docLen.put(p._1, findDocumentLength(p._2))
    })
    /*find average, median*/
    docLenAvg = file_docLen.values.sum / file_docLen.size
    docLenMedian = {
      val sorted = file_docLen.values.toSeq.sortWith(_ < _)
      if (sorted.length % 2 == 0) {
        val i = sorted.length / 2
        (sorted(i - 1) + sorted(i)) / 2
      }
      else
        sorted(sorted.length / 2)
    }
  }

  @throws(classOf[IllegalStateException])
  def maxTF(fileId: Int): Int = {
    file_maxTF.getOrElseUpdate(fileId,
      underlying.get(fileId) match {
        case None => throw new IllegalStateException("index has not been loaded")
        case Some(termPositions) => if (termPositions.isEmpty) {
          /*doc not found*/
          0
        } else {
          termPositions.values.map(_.length).max
        }
      })
  }

  updateStatis()
}

object DocumentIndexFactory {
  /**
    * @define key term
    * @define value positions[]
    **/
  type TermPositionMap = mutable.HashMap[String, ArrayBuffer[Int]]
  /**
    * @define key fileId
    * @define value termPositionMap
    **/
  type DocTermPositionMap = mutable.HashMap[Int, TermPositionMap]
  private var cachedInstance: DocumentIndex = null

  @throws(classOf[IllegalStateException])
  def getDocumentIndex: DocumentIndex = {
    if (cachedInstance == null) throw new IllegalStateException("index has not been loaded")
    cachedInstance
  }


  @throws(classOf[InvalidFileFormatException])
  @throws(classOf[RichFileNotFoundException])
  def load(file: File) = {
    cachedInstance = null
    try {
      val docInfoMap = new DocTermPositionMap
      var lineLeft = -2
      /* tmp vars */
      var termPositionMap: TermPositionMap = null
      val N = Utils.countLines(file)
      var i = 0
      var lp = 0f
      var p = 0f
      Utils.processLines(file, new Consumer[String] {
        override def accept(line: String): Unit = {
          i += 1
          p = 1f * i / N
          if ((p - lp) > Debug.process_step) {
            logp(p * 100f + "% \t" + Utils.getRamUsageString)
            lp = p
          }
          lineLeft match {
            case -2 | -1 =>
              lineLeft += 1
            case 0 =>
              val words = line.split(" ")
              val fileId = words(0).toInt
              lineLeft = words(1).toInt
              termPositionMap = new TermPositionMap
              docInfoMap.put(fileId, termPositionMap)
            case _ =>
              lineLeft -= 1
              val words = line.split(" ")
              val term = words.head
              val positions = words.tail.map(_.toInt)
              termPositionMap.put(term, ArrayBuffer.empty[Int] ++ positions)
          }
        }
      })
      cachedInstance = new DocumentIndex(docInfoMap)
    } catch {
      case e: NumberFormatException => throw new InvalidFileFormatException(file)
      case e: FileNotFoundException => throw new RichFileNotFoundException(file)
    }
  }

  def createFromTermIndex(termFileMap: TermFileMap) = {
    cachedInstance = null
    val docInfoMap = new DocTermPositionMap
    val N = termFileMap.size
    var i = 0
    var lp = 0f
    var p = 0f
    termFileMap.foreach(termFilePosition => {
      i += 1
      p = 1f * i / N
      if ((p - lp) > Debug.process_step) {
        logp(p * 100f + "% \t" + Utils.getRamUsageString)
        lp = p
      }
      termFilePosition._2.foreach(filePosition => {
        val termPositionMap: TermPositionMap = docInfoMap.getOrElse(filePosition._1, {
          val m = new TermPositionMap
          docInfoMap.put(filePosition._1, m)
          m
        })
        termPositionMap.getOrElseUpdate(termFilePosition._1, new ArrayBuffer[Int](1))
          .++=(filePosition._2)
      }
      )
    }
    )
    cachedInstance = new DocumentIndex(docInfoMap)
  }

  def docDistance(fileId1: Int, fileId2: Int) = {
    if (fileId1 == fileId2) 0 else 1
    //TODO document clustering
  }
}
