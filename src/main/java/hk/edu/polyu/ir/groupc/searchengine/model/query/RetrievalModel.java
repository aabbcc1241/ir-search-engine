package hk.edu.polyu.ir.groupc.searchengine.model.query;

import hk.edu.polyu.ir.groupc.searchengine.model.datasource.SearchResult;

/**
 * Created by beenotung on 10/23/15.
 */
public abstract class RetrievalModel {
    final String runningId;

    protected RetrievalModel(String runningId) {
        this.runningId = runningId;
    }

    public SearchResult search(Query query) {
        RetrievalDocument[] retrievalDocuments = new RetrievalDocument[0];
        return new SearchResult(runningId, query.queryId(), retrievalDocuments);
    }
}
