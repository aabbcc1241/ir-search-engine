package hk.edu.polyu.ir.groupc.searchengine;

import hk.edu.polyu.ir.groupc.searchengine.model.datasource.SearchResult;
import hk.edu.polyu.ir.groupc.searchengine.model.query.Query;
import hk.edu.polyu.ir.groupc.searchengine.model.query.RetrievalModel;

/**
 * Created by beenotung on 11/12/15.
 */
public class DummyTest {
    public static final String FILE_PATH = "res/file.txt";
    public static final String TERM_INDEX_PATH = "res/term_index.txt";
    public static final String POST_PATH = "res/psot1.txt";
    public static final String STOP_PATH = "res/estop.lst";
    public static final String JUDGEROBUST = "res/judgerobust";

    public static void main(String[] args) {
        new Launcher() {
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
        }.start(new RetrievalModel() {
            @Override
            public SearchResult search(Query query) {
                return super.search(query);
            }
        });
    }
}
