import hk.edu.polyu.ir.groupc.searchengine.model.Query;
import hk.edu.polyu.ir.groupc.searchengine.model.QueryFactory;
import scala.Function1;
import scala.Unit;
import scala.reflect.ClassTag;

import java.io.File;

/**
 * Created by beenotung on 11/6/15.
 */
public class QuestTest {
    public static void main(String[] args) {
        String pathT = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/Query/queryT";
        String pathTDN = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/Query/queryTDN";
        for (Query query : QueryFactory.createFromFile(new File(pathT)).toArray(new ClassTag<Query>())) {
            System.out.println(query.queryId()+" "+query.queryContent());
        }
    }
}
