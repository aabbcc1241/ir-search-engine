package hk.edu.polyu.ir.groupc.searchengine.model.query;

import java.util.List;

/**
 * Created by beenotung on 10/23/15.
 */
public abstract class RetrievalModel {
    public abstract List<RetrievalDocument> search(Query query);
}
