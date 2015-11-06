import java.io.File

import hk.edu.polyu.ir.groupc.searchengine.model.QueryFactory

/**
 * Created by beenotung on 11/6/15.
 */
object QueryTest extends App {
  override def main(args: Array[String]): Unit = {
    val pathT = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/Query/queryT"
    val pathTDN = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/Query/queryTDN"
    QueryFactory.createFromFile(new File(pathT)).foreach(println)
    QueryFactory.createFromFile(new File(pathTDN)).foreach(println)
  }
}
