package comm.gui

import java.io.{PrintWriter, StringWriter}
import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Label, TextArea}
import javafx.scene.layout.{GridPane, Priority}

import comm.lang.Convert.funcToRunnable


/**
  * Created by beenotung on 11/10/15.
  * show alert in javafx dialog
  */
object AlertUtils {
  def error_(text: String) = error(contentText = text)

  def error(title: String = "Error", headerText: String = null, contentText: String, exception: Exception = null) = {
    var node =
      if (exception == null)
        null
      else {
        val stringWriter: StringWriter = new StringWriter()
        exception.printStackTrace(new PrintWriter(stringWriter))
        val exceptionText = stringWriter.toString

        val label = new Label("Exception stacktrace:")

        val textArea = new TextArea(exceptionText)
        textArea.setEditable(false)
        textArea.setWrapText(true)
        textArea.setMaxWidth(Double.MaxValue)
        textArea.setMaxHeight(Double.MaxValue)
        GridPane.setVgrow(textArea, Priority.ALWAYS)
        GridPane.setHgrow(textArea, Priority.ALWAYS)

        val node = new GridPane
        node.setMaxWidth(Double.MaxValue)
        node.add(label, 0, 0)
        node.add(textArea, 0, 1)

        node
      }
    show(title, headerText, contentText, AlertType.ERROR, node)
  }

  def show(title: String, headerText: String = null, contentText: String, alertType: AlertType, expandableContent: Node = null) = {
    Platform runLater (() => {
      val alert = new Alert(alertType)
      alert.setTitle(title)
      if (headerText != null)
        alert.setHeaderText(headerText)
      alert.setResizable(true)
      alert.getDialogPane.setPrefHeight(200)
      alert.setContentText(contentText)
      if (expandableContent != null)
        alert.getDialogPane.setExpandableContent(expandableContent)
      val result = alert.showAndWait()
    })
  }

  def warn(title: String = "Warning", headerText: String = null, contentText: String) = {
    show(title, headerText, contentText, AlertType.WARNING)
  }

  def info(title: String = "Info", headerText: String = null, contentText: String) = {
    show(title, headerText, contentText, AlertType.INFORMATION)
  }
}
