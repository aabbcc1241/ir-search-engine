package comm

import java.io.{FileWriter, IOException}
import java.util.function.Consumer

/**
  * Created by beenotung on 11/13/15.
  */
object FileUtils {
  @throws(classOf[IOException])
  def appendToFile(lines: Seq[String],filename:String) = {
    val fw = new FileWriter(filename)
    try {
      lines.foreach(s => fw.write(s + "\n"))
      fw.close()
    } catch {
      case e: IOException => fw.close()
        throw e
    }
  }

  @throws(classOf[IOException])
  def appendToFile(lines: java.util.List[String],filename:String) = {
    val fw = new FileWriter(filename)
    try {
      lines.forEach(new Consumer[String] {
        override def accept(t: String): Unit = fw.write(t + "\n")
      })
      fw.close()
    } catch {
      case e: IOException => fw.close()
        throw e
    }
  }
}
