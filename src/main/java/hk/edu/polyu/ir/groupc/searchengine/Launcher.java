package hk.edu.polyu.ir.groupc.searchengine;

import comm.exception.EssentialFileNotFoundException;
import comm.exception.RichFileNotFoundException;
import comm.lang.ScalaSupport;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.*;
import hk.edu.polyu.ir.groupc.searchengine.model.query.Query;
import hk.edu.polyu.ir.groupc.searchengine.model.query.QueryFactory;
import hk.edu.polyu.ir.groupc.searchengine.model.query.RetrievalModel;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static hk.edu.polyu.ir.groupc.searchengine.Debug.*;

public abstract class Launcher {

    private boolean inited = false;

    protected abstract String FILE_PATH();

    protected abstract String TERM_INDEX_PATH();

    protected abstract String POST_PATH();

    protected abstract String STOP_PATH();

    protected abstract String JUDGEROBUST();

    protected abstract String QUERY();

    protected abstract boolean needDocumentIndex();

    public List<SearchResult> test(RetrievalModel retrievalModel, int numOfRetrievalDocument) {
        try {
            init();
            log("running retrieval model: " + retrievalModel.getClass().getName());
            List<SearchResult> searchResults = run(retrievalModel, numOfRetrievalDocument);
            deinit();
            return searchResults;
        } catch (EssentialFileNotFoundException e) {
            exception(e);
            loge("Error: Please make sure you have '" + e.path + "'");
        }
        return null;
    }

    public void start(RetrievalModel retrievalModel, String resultFilename, int numOfRetrievalDocument) {
        try {
            init();
            log("running retrieval model: " + retrievalModel.getClass().getName());
            List<SearchResult> searchResults = run(retrievalModel, numOfRetrievalDocument);
            log("saving search result to file <" + resultFilename + ">");
            try {
                SearchResultFactory.writeToFile(searchResults, resultFilename);
            } catch (IOException e) {
                exception(e);
                loge("Error: Failed to save search result!\nPlease make sure you have write permission on '" + resultFilename + "'");
            }
            deinit();
        } catch (EssentialFileNotFoundException e) {
            exception(e);
            loge("Error: Please make sure you have '" + e.path + "'");
        }
    }

    private void init() throws EssentialFileNotFoundException {
        if (inited) return;
        inited = true;
        try {
            log("loading file-doc list");
            DocFileFactory.load(new File(FILE_PATH()));
            log("loaded");
            try {
                log("loading term index");
                TermIndexFactory.load(new File(TERM_INDEX_PATH()));
                log("loaded");
                log("calculating IDF");
                TermIndexFactory.getTermIndex().cacheIDF();
                log("done");
                if (needDocumentIndex()) {
                    log("loading document index");
                    TermIndexFactory.getTermIndex().createDocumentIndex();
                    log("loaded");
                }
            } catch (comm.exception.RichFileNotFoundException e) {
                log("term index not found\nbuilding term index");
                TermIndexFactory.build(new File(POST_PATH()));
                log("built index, saving term index");
                TermIndexFactory.getTermIndex().writeToFile(TERM_INDEX_PATH());
                log("saved");
            }
            QueryFactory.loadFromFile(new File(QUERY()));
        } catch (RichFileNotFoundException e) {
            throw new EssentialFileNotFoundException(e.path);
        }
    }

    protected List<SearchResult> run(RetrievalModel retrievalModel, int numOfRetrievalDocument) {
        List<SearchResult> searchResults = new LinkedList<>();
        QueryFactory.getQueries().foreach(ScalaSupport.function1(new Function<Query, Object>() {
            @Override
            public Object apply(Query query) {
                log("searching on queryId: " + query.queryId());
                searchResults.add(retrievalModel.search(query, numOfRetrievalDocument));
                log("finished search");
                return null;
            }
        }));
        return searchResults;
    }

    private void deinit() {

    }
}
