package hk.edu.polyu.ir.groupc.searchengine

import comm.FileUtils

/**
  * Created by beenotung on 10/23/15.
  */
object Debug extends App {
  lazy val skipDebug = false
  lazy val skipError = false
  lazy val FILE_LOG = "log.txt"
  lazy val skipProgress = false
  lazy val process_step = 5f / 100f

  def exception(e: Exception) = {
    e.printStackTrace()
    loge(e)
  }

  def loge(x: Any) = if (!skipError) log(x)

  def logp(x: Any) = if (!skipProgress) {
    println(x)
    Console.out.flush()
  }

  def logd(x: Any) = if (!skipDebug) log(x)

  def log(x: Any) = {
    println(x)
    Console.out.flush()
    FileUtils.appendToFile(x.toString :: Nil, FILE_LOG)
  }

  /*@deprecated
  override def main(args: Array[String]) {
    log("Launcher start")

    log("Start pre-loading")

    log("Start GUI")
    val mainWindow=new MainApplication
    mainWindow.run()

    log("Launcher end")
  }*/
}
