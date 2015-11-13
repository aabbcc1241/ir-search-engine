package hk.edu.polyu.ir.groupc.searchengine;

import hk.edu.polyu.ir.groupc.searchengine.model.datasource.SearchResult;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.SearchResultFactory;
import hk.edu.polyu.ir.groupc.searchengine.model.query.Query;
import hk.edu.polyu.ir.groupc.searchengine.model.query.QueryFactory;
import hk.edu.polyu.ir.groupc.searchengine.model.query.RetrievalDocument;
import hk.edu.polyu.ir.groupc.searchengine.model.query.RetrievalModel;
import scala.util.Random;

import java.util.ArrayList;

/**
 * Created by beenotung on 11/12/15.
 */
public class DummyTest {
    public static final String FILE_PATH = "res/file.txt";
    public static final String TERM_INDEX_PATH = "res/term_index.txt";
    public static final String POST_PATH = "res/psot1.txt";
    public static final String STOP_PATH = "res/estop.lst";
    public static final String JUDGEROBUST = "res/judgerobust";
    public static final String QUERY_T="res/queryT";
    public static final String QUERY_TDN="res/queryTDN";
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
        };
        RetrievalModel retrievalModel = new RetrievalModel() {
            @Override
            public SearchResult search(Query query) {
                ArrayList<RetrievalDocument> retrievalDocuments = new ArrayList<>();
                Random random=new Random();
                retrievalDocuments.add(new RetrievalDocument(random.nextInt(10)+1,random.nextDouble()));
                return SearchResultFactory.create(query, retrievalDocuments);
            }
        };
        SearchResultFactory.setRunId("GroupC-demoModel");
        launcher.start(retrievalModel,RESULT_FILE);
    }
}
