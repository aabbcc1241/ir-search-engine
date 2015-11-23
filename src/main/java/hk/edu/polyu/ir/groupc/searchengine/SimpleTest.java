package hk.edu.polyu.ir.groupc.searchengine;

import comm.exception.EssentialFileNotFoundException;
import hk.edu.polyu.ir.groupc.searchengine.model.result.SearchResultFactory;
import hk.edu.polyu.ir.groupc.searchengine.model.retrievalmodel.RetrievalModel;
import hk.edu.polyu.ir.groupc.searchengine.model.retrievalmodel.SimpleModel;

/**
 * Created by beenotung on 11/12/15.
 */
public class SimpleTest {
    public static final String FILE_PATH = "res/file.txt";
    public static final String TERM_INDEX_PATH = "res/term_index.txt";
    public static final String POST_PATH = "res/post1.txt";
    public static final String STOP_PATH = "res/estop.lst";
    public static final String JUDGEROBUST = "res/judgerobust";
    public static final String QUERY_T = "res/queryT";
    public static final String QUERY_TDN = "res/queryTDN";
    public static final String RESULT_FILE = "res/result.txt";

    public static void main(String[] args) {
        Launcher launcher = new Launcher() {

            @Override
            public String filePath() {
                return FILE_PATH;
            }

            @Override
            public String termIndexPath() {
                return TERM_INDEX_PATH;
            }

            @Override
            public String postPath() {
                return POST_PATH;
            }

            @Override
            public String stopPath() {
                return STOP_PATH;
            }

            @Override
            public String judgeRobustPath() {
                return JUDGEROBUST;
            }

            @Override
            public String queryPath() {
                return QUERY_T;
            }

//            @Override
//            protected boolean needDocumentIndex() {
//                return true;
//            }
        };
        RetrievalModel retrievalModel = new SimpleModel();
        SearchResultFactory.setRunId("GroupC-DemoModel");
        try {
            launcher.start(retrievalModel, RESULT_FILE, 10);
        } catch (EssentialFileNotFoundException e) {
            Debug.loge_("res files does not exist");
        }
    }
}
