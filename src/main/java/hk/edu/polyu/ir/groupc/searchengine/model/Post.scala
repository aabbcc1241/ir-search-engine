package hk.edu.polyu.ir.groupc.searchengine.model

import java.io.File

import scala.io.Source

/**
  * Created by beenotung on 11/7/15.
  */
class Post(val term: String, val fileId: Int, val logicalWordPosition: Int) {

}

object PostFactory {
  protected var posts: Iterator[Post] = null

  @throws(classOf[IllegalStateException])
  def getPosts = {
    if (posts == null) throw new IllegalStateException("posts has not been loaded")
    posts
  }

  def loadFromFile(file: File) {
    posts = Source.fromFile(file).getLines().map(createFromString)
  }

  private def createFromString(rawString: String): Post = {
    val xs = rawString.split(" ")
    new Post(xs(0), xs(1).toInt, xs(2).toInt)
  }
}
