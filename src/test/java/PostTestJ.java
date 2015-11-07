
import hk.edu.polyu.ir.groupc.searchengine.model.TermInfoFactory;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by beenotung on 11/7/15.
 */
@Deprecated
public class PostTestJ {
    public static void main(String[] args) throws FileNotFoundException {
        String path = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/post1.txt";
//        PostFactory.loadFromFile(new File(path));
//        System.out.printf("length=%d", PostFactory.getPosts().length());
        final int[] l = {0};
//        Utils.foreach(PostFactory.getPosts(), post ->
//                System.out.printf("%d %s %s %s\n",
//                        ++l[0],
//                        post.term(),
//                        post.fileId(),
//                        post.logicalWordPosition())
//        );
        TermInfoFactory.buildTermIndex(new File(path));
//        System.out.println(PostFactory.getPostStream().count());
//        PostFactory.getPostStream().forEach(post ->
//                System.out.printf("%d %s %s %s\n",
//                        ++l[0],
//                        post.term(),
//                        post.fileId(),
//                        post.logicalWordPosition()));
    }
}
