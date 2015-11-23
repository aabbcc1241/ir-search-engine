package comm.gui

import javafx.stage.{DirectoryChooser, FileChooser, Stage}

/**
  * Created by beenotung on 11/22/15.
  */
object GuiUtils {
  def pickDirectory(stage: Stage): Option[String] = {
    val chooser = new DirectoryChooser()
    chooser.setTitle("Set Data Path")
    val directory = chooser.showDialog(stage)
    if (directory == null)
      None
    else
      Some(directory.getPath)
  }

  def pickFile(stage: Stage): Option[String] = {
    val chooser = new FileChooser()
    chooser.setTitle("Set Data Path")
    val directory = chooser.showOpenDialog(stage)
    if (directory == null)
      None
    else
      Some(directory.getPath)
  }
}
