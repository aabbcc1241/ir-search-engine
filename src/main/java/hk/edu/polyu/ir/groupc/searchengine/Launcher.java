package hk.edu.polyu.ir.groupc.searchengine;

import comm.Utils;
import comm.exception.EssentialFileNotFoundException;
import comm.exception.RichFileNotFoundException;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.DocFileFactory;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.SearchResult;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.TermInfoFactory;
import hk.edu.polyu.ir.groupc.searchengine.model.query.QueryFactory;
import hk.edu.polyu.ir.groupc.searchengine.model.query.RetrievalModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static hk.edu.polyu.ir.groupc.searchengine.Debug.log;

public abstract class Launcher {

    public abstract String FILE_PATH();

    public abstract String TERM_INDEX_PATH();

    public abstract String POST_PATH();

    public abstract String STOP_PATH();

    public abstract String JUDGEROBUST();
    public abstract String QUERY();

    public void start(RetrievalModel retrievalModel) {
        try {
            init();
            run(retrievalModel);
            deinit();
        } catch (EssentialFileNotFoundException e) {
//            exception(e);
            log("Error: Please make sure you have '" + e.path + "'");
        }
    }

    private void init() throws EssentialFileNotFoundException {
        try {
            log("loading file-doc list");
            DocFileFactory.load(new File(FILE_PATH()));
            log("loaded");
            try {
                log("loading term index");
                TermInfoFactory.load(new File(TERM_INDEX_PATH()));
                log("loaded");
            } catch (comm.exception.RichFileNotFoundException e) {
                log("term index not found\nbuilding term index");
                TermInfoFactory.build(new File(POST_PATH()));
                log("built index, saving term index");
                TermInfoFactory.getTermIndex().writeToFile(TERM_INDEX_PATH());
                log("saved");
            }
            QueryFactory.loadFromFile(new File(QUERY()));
        } catch (RichFileNotFoundException e) {
            throw new EssentialFileNotFoundException(e.path);
        }
    }

    protected List<SearchResult> run(RetrievalModel retrievalModel) {
        List<SearchResult> searchResults = new ArrayList<>();
        Utils.foreach(QueryFactory.getQueries(), query -> searchResults.add(retrievalModel.search(query)));
        return searchResults;
    }

    private void deinit() {

    }
}
