import comm.Utils;
import hk.edu.polyu.ir.groupc.searchengine.model.query.QueryFactory;

import java.io.File;

/**
 * Created by beenotung on 11/6/15.
 */
public class QueryTest {
    public static void main(String[] args) {
        String pathT = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/Query/queryT";
        String pathTDN = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/Query/queryTDN";
        QueryFactory.loadFromFile(new File(pathT));
        Utils.foreach(QueryFactory.getQueries(), query ->
                System.out.printf("%s %s\n",
                        query.queryId(),
                        query.queryContent()
                ));
    }
}
