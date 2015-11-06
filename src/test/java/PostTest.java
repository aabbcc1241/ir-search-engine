import hk.edu.polyu.ir.groupc.Utils;
import hk.edu.polyu.ir.groupc.searchengine.model.PostFactory;

import java.io.File;

/**
 * Created by beenotung on 11/7/15.
 */
public class PostTest {
    public static void main(String[] args) {
        String path = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/post1.txt";
        PostFactory.loadFromFile(new File(path));
//        System.out.printf("length=%d", PostFactory.getPosts().length());
        final int[] l = {0};
        Utils.foreach(PostFactory.getPosts(), post ->
                System.out.printf("%d %s %s %s\n",
                        ++l[0],
                        post.term(),
                        post.fileId(),
                        post.logicalWordPosition())
        );
    }
}
