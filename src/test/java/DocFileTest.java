import hk.edu.polyu.ir.groupc.searchengine.model.DocFile;
import hk.edu.polyu.ir.groupc.searchengine.model.DocFileFactory;

import java.io.File;

/**
 * Created by beenotung on 11/7/15.
 */
public class DocFileTest {
    public static void main(String[] args) {
        String path = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/file.txt";
        DocFileFactory.loadFromFile(new File(path));
        for (int i = 0; i < DocFileFactory.getDocN(); i++) {
            DocFile docFile = DocFileFactory.getDocFile(i);
            System.out.println(docFile.fileId()+" "+docFile.docLen()+" "+docFile.docId());
        }
    }
}
