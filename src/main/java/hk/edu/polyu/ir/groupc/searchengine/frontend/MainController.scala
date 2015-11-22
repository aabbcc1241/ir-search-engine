package hk.edu.polyu.ir.groupc.searchengine.frontend

import java.io.File
import java.util
import javafx.application.Platform.runLater
import javafx.event.ActionEvent
import javafx.scene.Cursor

import comm.exception.RichFileNotFoundException
import comm.gui.{AlertUtils, GuiUtils}
import hk.edu.polyu.ir.groupc.searchengine.Debug.{logDone, logMainStatus, loge}
import hk.edu.polyu.ir.groupc.searchengine.model.query.{QueryFactory, RetrievalModel, SimpleModel}
import hk.edu.polyu.ir.groupc.searchengine.{Debug, Launcher}

/**
  * Created by beenotung on 11/22/15.
  */

import comm.lang.Convert.funcToRunnable

object MainController {
  val MODES = new util.ArrayList[RetrievalModel]
  MODES.add(new SimpleModel)
  private var instance: MainController = null
  var resultId = 0
  var launcher: Launcher = null

  def getInstance() = {
    if (instance == null)
      throw new IllegalStateException("MainController has not been created")
    instance
  }

  def statusMain(msg: String) = {
    runLater(() => {
      instance.label_left_status setText msg
    })
  }

  def statusMinor(msg: String) = {
    runLater(() => {
      instance.label_right_status setText msg
    })
  }

  def bothStatus(main: String, minor: String) = {
    runLater(() => {
      instance.label_left_status setText main
      instance.label_right_status setText minor
    })
  }

  def statusReset = bothStatus("", "")

  def statusDone(lastAction: String) = {
    runLater(() => {
      instance.label_right_status setText lastAction
      instance.label_left_status setText "done"
    })
  }
}

class MainController extends MainControllerSkeleton {
  MainController.instance = this

  def getMajorStatus = label_left_status getText

  override def set_query_file(event: ActionEvent) = {
    val action = "load query file"
    logMainStatus(action)
    text_query_file setText (GuiUtils.pickFile(MainApplication.getInstance().getStage) match {
      case None => ""
      case Some(path) =>
        try {
          QueryFactory.loadFromFile(new File(path))
          logDone(action)
        } catch {
          case e: RichFileNotFoundException => loge("Failed to load query", e)
        }
        path
    })
  }

  override def set_result_path(event: ActionEvent) = {
    text_result_path setText (GuiUtils.pickDirectory(MainApplication.getInstance().getStage) match {
      case None => ""
      case Some(path) =>
        logDone("set result path")
        path
    })
  }

  override def set_data_path(event: ActionEvent) = {
    text_data_path setText (GuiUtils.pickDirectory(MainApplication.getInstance().getStage) match {
      case None => ""
      case Some(path) =>
        val scene = MainApplication.getScene
        scene.setCursor(Cursor.WAIT)
        new Thread(MainApplication.threadGroup, "loadIndexThread") {
          override def run() = {
            try {
              logMainStatus(s"set data path to <$path>")
              MainController.launcher = new Launcher {
                filePath(s"$path/file.txt")
                termIndexPath(s"$path/term_index.txt")
                postPath(s"$path/post1.txt")
                stopPath(s"$path/estop.lst")
                judgeRobustPath(s"$path/judgerobust")
                init()
              }
              MainController.statusDone("load index")
              AlertUtils.info("Success", "Loaded data", "The search engines are ready")
            } catch {
              case e: RichFileNotFoundException =>
                loge(s"Please make sure you have <${e.path}>", e)
                runLater(() => {
                  label_right_status setText "set data path"
                })
              case e: ThreadDeath =>
                Debug.log("loadIndexThread stopped during execution")
            } finally {
              runLater(() => {
                scene.setCursor(Cursor.DEFAULT)
              })
            }
          }
        }.start()
        path
    })
  }
}
