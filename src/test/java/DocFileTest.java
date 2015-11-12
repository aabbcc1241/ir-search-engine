import comm.exception.RichFileNotFoundException;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.DocFile;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.DocFileFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by beenotung on 11/7/15.
 */
public class DocFileTest {


    public static void main(String[] args) throws RichFileNotFoundException {
        String path = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/file.txt";
//        String path = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/file.txt_";
        DocFileFactory.load(new File(path));
        HashMap file_doc_map = new HashMap();
        Random random = new Random();
        int DOCUMENT_COUNT = DocFileFactory.getDocumentCount();
        float lastReport = 0f;
        float current;
        for (int i = 0; i < DOCUMENT_COUNT; i++) {
            DocFile docFile = DocFileFactory.getDocFile(i);
//            System.out.println(docFile.fileId()+" "+docFile.docLen()+" "+docFile.docId());
            file_doc_map.put(docFile.fileId(), docFile.docId());
            current = 1f * i / DOCUMENT_COUNT;
            if ((current - lastReport) > 0.01f) {
                lastReport = current;
                System.out.println(current * 100f + "%");
            }
        }
        System.out.println("Done\nCompared " + DOCUMENT_COUNT + " record");
        System.out.println("Total number of collapsed file-doc pair = " + (DOCUMENT_COUNT - file_doc_map.size()));
    }
}
