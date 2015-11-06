package hk.edu.polyu.ir.groupc.searchengine.model

import java.io.File
import java.util.concurrent.locks.ReentrantReadWriteLock

import scala.io.Source

/**
 * Created by beenotung on 11/6/15.
 */
@deprecated
object QueryFactory {
  def createFromFile(file: File): Iterator[Query] = {
    Source.fromFile(file).getLines().map[Query] (createFromString)
  }

  def createFromString(rawString: String): Query = {
    val id = rawString.substring(0, 3)
    val content = rawString.substring(4, rawString.length)
    new Query(id.trim, content.trim)
  }
}


@deprecated
class Query(val id: String, val content: String) {
  override def toString: String = {
    /*    Map(
    //      "raw" -> super.toString,
          "id" -> id,
          "content" -> content
        ).toString()*/
    s"$id $content"
  }
}


object Query {
  private val lock = new ReentrantReadWriteLock()
  private var rawStrings: List[String] = ???

  def loadFile(file: File) = {
    lock.writeLock().lock()
    rawStrings = Source.fromFile(file).getLines().toList
    lock.writeLock().unlock()
  }

  def clearFile() = {
    lock.writeLock().lock()
    rawStrings = ???
    lock.writeLock().unlock()
  }

  def getId(index: Int): String = {
    lock.readLock().lock()
    val rawString = rawStrings(index)
    lock.readLock().unlock()
    rawString.substring(0,3)
  }

  def getContent(index: Int) = {
    lock.readLock().lock()
    val rawString = rawStrings(index)
    lock.readLock().unlock()
    rawString.substring(4, rawString.length)
  }
}

