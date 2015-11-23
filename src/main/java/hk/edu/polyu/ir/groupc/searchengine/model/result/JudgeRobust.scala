package hk.edu.polyu.ir.groupc.searchengine.model.result

import java.io.{File, FileNotFoundException}

import comm.exception.{InvalidFileFormatException, RichFileNotFoundException}

import scala.io.Source

/**
  * Created by beenotung on 11/22/15.
  */
object JudgeRobustFactory {
  private var path: String = null


  @throws(classOf[RichFileNotFoundException])
  @throws(classOf[InvalidFileFormatException])
  def load(file: File) = {
    try {
      if (Source.fromFile(file).getLines().length <= 0)
        throw new InvalidFileFormatException(file)
      path = file.getPath
    }
    catch {
      case e: FileNotFoundException => throw new RichFileNotFoundException(file)
    }
  }
}
