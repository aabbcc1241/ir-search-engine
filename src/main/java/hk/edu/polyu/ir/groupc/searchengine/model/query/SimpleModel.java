package hk.edu.polyu.ir.groupc.searchengine.model.query;

import comm.lang.ScalaSupport;
import hk.edu.polyu.ir.groupc.searchengine.model.Index;
import scala.Tuple2;
import scala.collection.mutable.ArrayBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by beenotung on 11/12/15.
 */
public class SimpleModel extends RetrievalModel {
    @Override
    public List<RetrievalDocument> search(Query query) {
        ArrayList<RetrievalDocument> retrievalDocuments = new ArrayList<>();
        for (ExpandedTerm expandedTerm : query.expandedTerms()) {
            ScalaSupport.foreachMap(expandedTerm.term().filePositionMap(), new Consumer<Tuple2<Object, ArrayBuffer<Object>>>() {
                @Override
                public void accept(Tuple2<Object, ArrayBuffer<Object>> e) {
                    double similarityScore = e._2().size() * expandedTerm.weight();
                    similarityScore/= Index.getDocumentLength((Integer) e._1());
                    retrievalDocuments.add(new RetrievalDocument((int) e._1(), similarityScore));
                }
            });
        }
        return retrievalDocuments;
    }
}
