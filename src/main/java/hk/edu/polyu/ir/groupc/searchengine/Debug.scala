package hk.edu.polyu.ir.groupc.searchengine

/**
  * Created by beenotung on 10/23/15.
  */
object Debug extends App {
  val debug = true
  val error = true

  def exception(e: Exception) = {
    e.printStackTrace()
    loge(e)
  }

  def loge(x: Any) = if (error) log(x)

  def logd(x: Any) = if (debug) log(x)

  def log(x: Any) = println(x)

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
