package comm

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType

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
    val alert = new Alert(alertType)
    alert.setTitle(title)
    if (headerText != null)
      alert.setHeaderText(headerText)
    alert.setContentText(contentText)
    alert.showAndWait()
  }
}
