package hk.edu.polyu.ir.groupc.searchengine

import hk.edu.polyu.ir.groupc.searchengine.view.MainWindow

/**
 * Created by beenotung on 10/23/15.
 */
object Launcher extends App {
  val debug = true
  val error = true

  def logd(x: Any) = if (debug) log(x)

  def loge(x: Any) = if (error) log(x)

  def log(x: Any) = println(x)

  override def main(args: Array[String]) {
    log("Launcher start")

    log("Start pre-loading")

    log("Start GUI")
    val mainWindow=new MainWindow
    mainWindow.startUI()

    log("Launcher end")
  }
}
