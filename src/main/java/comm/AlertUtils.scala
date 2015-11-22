package comm

import javafx.application.Platform
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType

import comm.lang.Convert.funcToRunnable


/**
  * Created by beenotung on 11/10/15.
  * show alert in javafx dialog
  */
object AlertUtils {
  def error_(text: String) = error(contentText = text)

  def error(title: String = "Error", headerText: String = null, contentText: String) = {
    show(title, headerText, contentText, AlertType.ERROR)
  }

  def show(title: String, headerText: String = null, contentText: String, alertType: AlertType) = {
    Platform runLater (() => {
      val alert = new Alert(alertType)
      alert.setTitle(title)
      if (headerText != null)
        alert.setHeaderText(headerText)
      alert.setResizable(true)
      alert.getDialogPane.setPrefHeight(200)
      alert.setContentText(contentText)
      val result = alert.showAndWait()
    })
  }
}
