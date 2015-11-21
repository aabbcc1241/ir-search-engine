
import comm.Test;
import hk.edu.polyu.ir.groupc.searchengine.Launcher;
import hk.edu.polyu.ir.groupc.searchengine.model.result.SearchResult;
import hk.edu.polyu.ir.groupc.searchengine.model.query.RetrievalModel;
import hk.edu.polyu.ir.groupc.searchengine.model.query.SimpleModel;
import hk.edu.polyu.ir.groupc.searchengine.model.result.SearchResultFactory;
import scala.Tuple2;

import java.util.List;
import java.util.function.Supplier;

import static hk.edu.polyu.ir.groupc.searchengine.Debug.log;

/**
 * Created by beenotung on 11/12/15.
 */
public class SimpleModelTest {
    public static final String FILE_PATH = "res/file.txt";
    public static final String TERM_INDEX_PATH = "res/term_index.txt";
    public static final String POST_PATH = "res/post1.txt";
    public static final String STOP_PATH = "res/estop.lst";
    public static final String JUDGEROBUST = "res/judgerobust";
    public static final String QUERY_T = "res/queryT";
    public static final String QUERY_TDN = "res/queryTDN";
    private static final String RESULT_FILE = "res/result.txt";

    public static void main(String[] args) {
        Launcher launcher = new Launcher() {
            @Override
            public String FILE_PATH() {
                return FILE_PATH;
            }

            @Override
            public String TERM_INDEX_PATH() {
                return TERM_INDEX_PATH;
            }

            @Override
            public String POST_PATH() {
                return POST_PATH;
            }

            @Override
            public String STOP_PATH() {
                return STOP_PATH;
            }

            @Override
            public String JUDGEROBUST() {
                return JUDGEROBUST;
            }

            @Override
            public String QUERY() {
                return QUERY_T;
            }

            @Override
            protected boolean needDocumentIndex() {
                return false;
            }
        };
        RetrievalModel retrievalModel = new SimpleModel();
        SearchResultFactory.setRunId("GroupC-DemoModel");
        Tuple2<Object, Object>[] results = Test.time_J(new Supplier<Object>() {
            @Override
            public Object get() {
                List<SearchResult> searchResults = launcher.test(retrievalModel, 10);
                System.out.println(searchResults.size());
                return searchResults;
            }
        }, 10, true, true, true);
        for (Tuple2<Object, Object> result : results) {
            log("used time " + (long) result._2() + " ns");
        }
    }
}
