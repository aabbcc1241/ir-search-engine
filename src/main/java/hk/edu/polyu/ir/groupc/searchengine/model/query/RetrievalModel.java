package hk.edu.polyu.ir.groupc.searchengine.model.query;

import hk.edu.polyu.ir.groupc.searchengine.model.datasource.SearchResult;

/**
 * Created by beenotung on 10/23/15.
 */
public abstract class RetrievalModel {
    public abstract SearchResult search(Query query,int numResult);
}
