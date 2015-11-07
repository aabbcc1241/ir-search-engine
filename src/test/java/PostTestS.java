
import hk.edu.polyu.ir.groupc.searchengine.model.PostFactory;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by beenotung on 11/7/15.
 */
public class PostTestS {
    public static void main(String[] args) throws FileNotFoundException {
        String path = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/post1.txt";
        PostFactory.buildIndex(new File(path));
//        System.out.printf("length=%d", PostFactory.getPostStream().count());
        final int[] l = {0};
//        PostFactory.getPostStream().forEach(post ->
//            System.out.printf("%d %s %s %s\n",
//                        ++l[0],
//                        ((Post) post).term(),
//                        ((Post) post).fileId(),
//                        ((Post) post).logicalWordPosition())
//        );
//        PostFactory.loadFromFile(new File(path)).forEach(post ->
//                System.out.printf("%d %s %s %s\n",
//                        ++l[0],
//                        post.term(),
//                        post.fileId(),
//                        post.logicalWordPosition()));
    }
}
