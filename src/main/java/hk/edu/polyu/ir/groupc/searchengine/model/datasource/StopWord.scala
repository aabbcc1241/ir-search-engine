package hk.edu.polyu.ir.groupc.searchengine.model.datasource

import java.io.{File, FileNotFoundException}

import comm.exception.RichFileNotFoundException

import scala.io.Source

/**
  * Created by beenotung on 11/21/15.
  */
object StopWordFactory {
  private var stopWords: Set[String] = null

  def isStopWord(term: String) =
    if (stopWords == null)
      throw new IllegalStateException("stop word has not been loaded")
    else
      stopWords contains term

  @throws(classOf[RichFileNotFoundException])
  def load(file: File) = {
    try
      stopWords = (Source fromFile file getLines() map (_.toLowerCase)) toSet
    catch {
      case e: FileNotFoundException => throw new RichFileNotFoundException(file)
    }
  }
}
