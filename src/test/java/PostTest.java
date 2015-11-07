import hk.edu.polyu.ir.groupc.searchengine.model.PostFactory;

import java.io.File;

/**
 * Created by beenotung on 11/8/15.
 */
public class PostTest {
    public static void main(String [] args){
        String path="/home/beenotung/Documents/polyu/IR/group assignment/res/post1.txt";
        String path_out="/home/beenotung/Documents/polyu/IR/group assignment/res/term_index.txt";
        PostFactory.buildTermIndex(new File(path));
        PostFactory.getTermIndex().toFile(path_out);
    }
}
