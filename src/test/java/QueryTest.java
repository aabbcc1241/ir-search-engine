import comm.exception.RichFileNotFoundException;
import comm.lang.ScalaSupport;
import hk.edu.polyu.ir.groupc.searchengine.model.query.Query;
import hk.edu.polyu.ir.groupc.searchengine.model.query.QueryEnum;
import hk.edu.polyu.ir.groupc.searchengine.model.query.QueryFactory;

import java.io.File;
import java.util.function.Function;

/**
 * Created by beenotung on 11/6/15.
 */
public class QueryTest {
    public static void main(String[] args) throws RichFileNotFoundException {
        String pathT = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/Query/queryT";
        String pathTDN = "/home/beenotung/Dropbox/_SyncShare/Documents/polyu/IR/group assignment/res/Query/queryTDN";
        QueryFactory.loadFromFile(new File(pathT), QueryEnum.T().id());
        QueryFactory.getQueries_T().foreach(ScalaSupport.function1(new Function<Query, Object>() {
            @Override
            public Object apply(Query query) {
                System.out.printf("%s %s\n",
                        query.queryId(),
                        query.rawQueryContent()
                );
                return null;
            }
        }));
    }
}
