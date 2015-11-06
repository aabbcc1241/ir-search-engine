package hk.edu.polyu.ir.groupc.searchengine.model;

/**
 * Created by beenotung on 10/30/15.
 */
public class RetrievalDocument{
    public String documentId;
    /*higher is better*/
    public double similarityScore;

    public RetrievalDocument(String documentId, double similarityScore) {
        this.documentId = documentId;
        this.similarityScore = similarityScore;
    }
}
