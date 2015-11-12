package hk.edu.polyu.ir.groupc.searchengine.model.query;

import hk.edu.polyu.ir.groupc.searchengine.model.datasource.SearchResult;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.SearchResultFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beenotung on 10/23/15.
 */
public abstract class RetrievalModel {
    final String runningId="GroupC";

    public SearchResult search(Query query) {
        String str=query.expandedQuery();
        List<RetrievalDocument> retrievalDocuments=new ArrayList<>();
        SearchResultFactory.create(query.queryId(),retrievalDocuments);
        return new SearchResult(runningId, query.queryId(), retrievalDocuments);
    }
}
