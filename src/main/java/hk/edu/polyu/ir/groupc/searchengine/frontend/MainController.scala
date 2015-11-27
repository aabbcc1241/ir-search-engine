package hk.edu.polyu.ir.groupc.searchengine.frontend

import java.io.File
import java.util
import javafx.application.Platform.runLater
import javafx.beans.property.{ReadOnlyObjectWrapper, SimpleIntegerProperty}
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.scene.Cursor
import javafx.scene.control.TableColumn
import javafx.scene.control.TableColumn.CellDataFeatures
import javafx.util.Callback

import comm.exception.RichFileNotFoundException
import comm.gui.{AlertUtils, GuiUtils}
import hk.edu.polyu.ir.groupc.searchengine.Debug.{log, logDone, logMainStatus, loge}
import hk.edu.polyu.ir.groupc.searchengine.model.query.{QueryEnum, QueryFactory}
import hk.edu.polyu.ir.groupc.searchengine.model.retrievalmodel.{Parameter, RetrievalModel}
import hk.edu.polyu.ir.groupc.searchengine.{Config, Launcher}

import scala.language.postfixOps

/**
  * Created by beenotung on 11/22/15.
  */

import comm.lang.Convert.funcToRunnable

object MainController {
  var defaultNumOfRetrievalDocument: Int = 1000
  assert(defaultNumOfRetrievalDocument > 0, "default number of retrieval document must be positive integer")
  private val MODELS: util.ArrayList[RetrievalModel] = new util.ArrayList[RetrievalModel]()
  var resultId = 0
  var launcher: Launcher = null
  private var instance: MainController = null

  def addModel(retrievalModel: RetrievalModel) = {
    if (!MODELS.contains(retrievalModel))
      MODELS.add(retrievalModel)
  }

  def removeModel(retrievalModel: RetrievalModel) = {
    while (MODELS.contains(retrievalModel))
      MODELS.remove(retrievalModel)
  }

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

  def statusReset() = bothStatus("", "")

  def bothStatus(main: String, minor: String) = {
    runLater(() => {
      instance.label_left_status setText main
      instance.label_right_status setText minor
    })
  }

  def statusDone(lastAction: String) = {
    runLater(() => {
      instance.label_right_status setText lastAction
      instance.label_left_status setText "done"
    })
  }

  def newRunId(groupName: String, model: RetrievalModel): String = {
    resultId += 1
    s"${groupName}_${model.name()}_${model.getMode}_result_$resultId"
  }
}

class MainController extends MainControllerSkeleton {
  MainController.instance = this

  //  private var numOfRetrievalDocument = MainController.defaultNumOfRetrievalDocument
  val numOfRetrievalDocument = new SimpleIntegerProperty(MainController.defaultNumOfRetrievalDocument)

  def getMajorStatus = label_left_status getText

  override def set_query_T_file(event: ActionEvent) = {
    set_query_file(event, QueryEnum.T.id)
  }

  override def set_query_TDN_file(event: ActionEvent) = {
    set_query_file(event, QueryEnum.TDN.id)
  }

  def set_query_file(event: ActionEvent, queryType: Int) = {
    val action = "load query file"
    logMainStatus(action)
    val path = GuiUtils.pickFile(MainApplication.getInstance().getStage) match {
      case None => ""
      case Some(path) =>
        try {
          QueryFactory.loadFromFile(new File(path), queryType)
          logDone(action)
        } catch {
          case e: RichFileNotFoundException => loge("Failed to load query", e)
        }
        path
    }
    if (QueryEnum.TDN.id.equals(queryType))
      text_query_TDN_file.setText(path)
    else
      text_query_T_file.setText(path)
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
                log("loadIndexThread stopped during execution")
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

  override def initialize() = {
    super.initialize()

    Config.CheckSystemInfo()

    text_number_of_retrieval_document.setText(numOfRetrievalDocument.intValue().toString)

    /* init combo box items */
    combo_model.getItems.clear()
    combo_model.getItems.addAll(MainController.MODELS)
    combo_model_mode.getItems.clear()

    tablecolumn_model_param_name.setCellValueFactory(new Callback[TableColumn.CellDataFeatures[Parameter[_ <: Number], String], ObservableValue[String]] {
      override def call(p: TableColumn.CellDataFeatures[Parameter[_ <: Number], String]): ObservableValue[String] = {
        p.getValue.name
      }
    })

    tablecolumn_model_param_min.setCellValueFactory(new Callback[CellDataFeatures[Parameter[_ <: Number], Number], ObservableValue[Number]] {
      override def call(p: CellDataFeatures[Parameter[_ <: Number], Number]): ObservableValue[Number] = {
        new ReadOnlyObjectWrapper[Number](p.getValue.min.getValue)
      }
    })
    tablecolumn_model_param_max.setCellValueFactory(new Callback[CellDataFeatures[Parameter[_ <: Number], Number], ObservableValue[Number]] {
      override def call(p: CellDataFeatures[Parameter[_ <: Number], Number]): ObservableValue[Number] = {
        new ReadOnlyObjectWrapper[Number](p.getValue.max.getValue)
      }
    })
    tablecolumn_model_param_suggested.setCellValueFactory(new Callback[CellDataFeatures[Parameter[_ <: Number], Number], ObservableValue[Number]] {
      override def call(p: CellDataFeatures[Parameter[_ <: Number], Number]): ObservableValue[Number] = {
        new ReadOnlyObjectWrapper[Number](p.getValue.suggested.getValue)
      }
    })

    //    tablecolumn_model_param_value.setCellFactory(TextFieldTableCell.forTableColumn[Parameter[_ <: Number]]())
    tablecolumn_model_param_value.setCellValueFactory(new Callback[CellDataFeatures[Parameter[_ <: Number], Number], ObservableValue[Number]] {

      override def call(p: CellDataFeatures[Parameter[_ <: Number], Number]): ObservableValue[Number] = {
        val wrapper = new ReadOnlyObjectWrapper[Number]()
        wrapper.bind(p.getValue.value)
        wrapper
      }
    })

    table_model.getSelectionModel.selectedItemProperty().addListener(new ChangeListener[Parameter[_ <: Number]] {
      override def changed(observableValue: ObservableValue[_ <: Parameter[_ <: Number]], oldSelection: Parameter[_ <: Number], newSelection: Parameter[_ <: Number]): Unit = {
        if (newSelection != null) {
          text_model_param_editing.setText(newSelection.value().toString)
        } else {
          text_model_param_editing.setText("")
        }
      }
    })

    //    setNumOfRetrievalDocument(MainController.defaultNumOfRetrievalDocument)
    combo_model.getSelectionModel.selectFirst()
    set_model(null)
  }

  def updateNumOfRetrievalDocument() = {
    val value =
      try {
        Integer.parseInt(text_number_of_retrieval_document.getText())
      } catch {
        case e: NumberFormatException =>
          try {
            Math.round(text_number_of_retrieval_document.getText().toDouble).toInt
          } catch {
            case e: NumberFormatException =>
              numOfRetrievalDocument.intValue()
          }
      }
    setNumOfRetrievalDocument(value)
  }

  override def set_model(event: ActionEvent) = {
    val model = combo_model.getSelectionModel.getSelectedItem
    if (model != null) {
      table_model.getItems.clear()
      table_model.setItems(FXCollections.observableArrayList(model.getParameters))
      combo_model_mode.getItems.clear()
      combo_model_mode.getItems.addAll(model.getModes)
      logDone("selected model " + model.name())
      set_model_mode(event)
    }
  }

  override def set_model_mode(event: ActionEvent) = {
    selectedModel match {
      case None => AlertUtils.warn(contentText = "Please select retrieval mode; first")
      case Some(model) =>
        val mode: String = combo_model_mode.getSelectionModel.getSelectedItem
        if (mode == null) {
          model.setMode(model.getDefaultMode)
          combo_model_mode.getSelectionModel.selectFirst()
        } else {
          model.setMode(mode)
          logDone("set mode to " + mode)
        }
    }
  }

  override def set_model_param_editing(event: ActionEvent) = {
    //TODO
    val rawNewValue: String = text_model_param_editing.getText
    log(s"new value=$rawNewValue")
    val selected = table_model.getSelectionModel.getSelectedItem
    if (selected != null) {
      selected.value(rawNewValue)
      text_model_param_editing.setText(selected.value().toString)
    }
  }

  override def start_search_T(event: ActionEvent) = {
    start_search(event, QueryEnum.T.id)
  }

  override def start_search_TDN(event: ActionEvent) = {
    start_search(event, QueryEnum.TDN.id)
  }

  def start_search(event: ActionEvent, queryType: Int) = {
    if (preSearchCheck(queryType)) {
      selectedModel match {
        case None => AlertUtils.warn(contentText = "Please choose a retrieval model")
        case Some(model) =>
          val resultFilename = getResultFilename(model, queryType)
          val numOfRetrievalDocument = getNumOfRetrievalDocument
          new Thread(MainApplication.threadGroup, () => {
            try {
              //              val num_list = Range(1, 10001, 5)
              val num_list = List(1, 5, 10, 15, 20, 30,
                100, 200, 500,
                1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900,
                2000, 2500, 3000, 4000, 5000, 10000)
              //              val success = MainController.launcher.test(model, num_list.toArray, queryType)
              val timeUsed = MainController.launcher.start(model, resultFilename, numOfRetrievalDocument.intValue(), queryType)
              val timeMsg = "Finished Search in " + (timeUsed / Math.pow(10, 9)) + " seconds"
              log(timeMsg)
              AlertUtils.info(contentText = timeMsg)
              //              if (success)
              //                AlertUtils.info(contentText = "Finished retrieval")
              //              else
              //                loge("Failed to retrieval")
            } catch {
              case e: Exception => loge("Unknown Error!", e)
            }
          }, "SearchThread")
            .start()
      }
    }
  }

  def preSearchCheck(queryType: Int): Boolean = {
    if (MainController.launcher == null) {
      loge("Index not given", "Please pick Data path in setting page")
      return false
    }
    if (!QueryFactory.ready(queryType)) {
      loge("Query files not given", "Please pick Query file in setting page")
      return false
    }
    if (text_result_path.getText().equals("")) {
      loge("Path for result files not given", "Please pick Result path in setting page")
      return false
    }
    true
  }

  def getResultFilename(model: RetrievalModel, queryType: Int): String = {
    //    MainController.resultId += 1
    //    val currentResultId = MainController.resultId
    //    s"${text_result_path.getText()}/result-${model.name()}-$currentResultId.txt"
    val filename: String = QueryEnum.getFileName(queryType)
    s"${text_result_path.getText()}${File.separatorChar}$filename"
  }

  def getNumOfRetrievalDocument = numOfRetrievalDocument

  def setNumOfRetrievalDocument(value: Int): Unit = {
    if (value <= 0) {
      setNumOfRetrievalDocument(MainController.defaultNumOfRetrievalDocument)
    } else {
      //      numOfRetrievalDocument = value
      numOfRetrievalDocument.set(value)
      //      text_number_of_retrieval_document.setText(value.toString)
      logDone("set number of retrieval document : " + getNumOfRetrievalDocument)
    }
  }

  def selectedModel: Option[RetrievalModel] = Option(combo_model.getSelectionModel.getSelectedItem)

  override def reset_number_of_retrieval_document(event: ActionEvent) = {
    setNumOfRetrievalDocument(MainController.defaultNumOfRetrievalDocument)
  }

  override def set_number_of_retrieval_document(event: ActionEvent) = {
    updateNumOfRetrievalDocument()
  }
}
