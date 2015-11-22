package hk.edu.polyu.ir.groupc.searchengine.frontend

import java.io.File
import java.util
import javafx.application.Platform.runLater
import javafx.beans.property.ReadOnlyObjectWrapper
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
import hk.edu.polyu.ir.groupc.searchengine.Launcher
import hk.edu.polyu.ir.groupc.searchengine.model.query.QueryFactory
import hk.edu.polyu.ir.groupc.searchengine.model.retrievalmodel.{Parameter, RetrievalModel, SimpleModel}

/**
  * Created by beenotung on 11/22/15.
  */

import comm.lang.Convert.funcToRunnable

object MainController {
  var MODELS = new util.ArrayList[RetrievalModel]
  MODELS.add(new SimpleModel)
  var resultId = 0
  var launcher: Launcher = null
  private var instance: MainController = null

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
}

class MainController extends MainControllerSkeleton {
  MainController.instance = this

  /*//   @FXML // fx:id="combo_model"
      protected var combo_model: ComboBox[RetrievalModel] ; // Value injected by FXMLLoader

  //    @FXML // fx:id="table_model"
      protected var   table_model    : TableView[RetrievalModel.Parameter[_<:Number]] ; // Value injected by FXMLLoader

  //    @FXML // fx:id="tablecolumn_model_param_name"
      protected var   tablecolumn_model_param_name    : TableColumn[RetrievalModel.Parameter[_<:Number], String] ; // Value injected by FXMLLoader

      //@FXML // fx:id="tablecolumn_model_param_min"
      protected var   tablecolumn_model_param_min    : TableColumn[RetrievalModel.Parameter[_<:Number], Number] ; // Value injected by FXMLLoader

      //@FXML // fx:id="tablecolumn_model_param_max"
      protected var   tablecolumn_model_param_max    : TableColumn[RetrievalModel.Parameter[_<:Number], Number] ; // Value injected by FXMLLoader

      //@FXML // fx:id="tablecolumn_model_param_suggested"
      protected var   tablecolumn_model_param_suggested    : TableColumn[RetrievalModel.Parameter[_<:Number], Number] ; // Value injected by FXMLLoader

      //@FXML // fx:id="tablecolumn_model_param_value"
      protected var     tablecolumn_model_param_value  : TableColumn[RetrievalModel.Parameter[_<:Number], Number] ; // Value injected by FXMLLoader*/


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
    combo_model.getItems.clear()
    combo_model.getItems.addAll(MainController.MODELS)

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


    //    table_model.getItems.addAll("A","B")
  }

  override def func_test(event: ActionEvent) = {
    log(event)
  }

  override def set_model(event: ActionEvent) = {
    val model = combo_model.getSelectionModel.getSelectedItem
    table_model.getItems.clear()
    table_model.setItems(FXCollections.observableArrayList(model.getParameters))
    //    table_model.getItems.addAll(model.getParameters)
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
}
