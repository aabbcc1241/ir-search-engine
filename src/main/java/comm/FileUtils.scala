package comm

import java.io.{BufferedWriter, FileWriter, IOException, PrintWriter}

/**
  * Created by beenotung on 11/13/15.
  */
object FileUtils {
  @throws(classOf[IOException])
  def appendToFile(lines: java.util.List[String], filename: String): Unit = {
    import scala.collection.JavaConverters._
    appendToFile(lines.asScala, filename)
  }

  @throws(classOf[IOException])
  def appendToFile(lines: Seq[String], filename: String) = {
    val out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))
    try {
      lines.foreach(s => out.write(s + "\n"))
      out.close()
    } catch {
      case e: IOException => out.close()
        throw e
    }
  }
}
