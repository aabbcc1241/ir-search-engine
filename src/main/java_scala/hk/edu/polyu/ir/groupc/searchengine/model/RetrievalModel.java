package hk.edu.polyu.ir.groupc.searchengine.model;
/**
 * Created by beenotung on 10/23/15.
 */
public interface RetrievalModel {
    public RetrievalDocument[]search(String query);
}
