package hk.edu.polyu.ir.groupc.searchengine.model.query;

import comm.lang.ScalaSupport;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.SearchResult;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.SearchResultFactory;
import scala.Tuple2;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * Created by beenotung on 11/12/15.
 */
public class DummyModel extends RetrievalModel {
    @Override
    public SearchResult search(Query query, int numResult) {
        ConcurrentLinkedQueue<Object> retrievalDocuments = new ConcurrentLinkedQueue<>();
        for (ExpandedTerm expandedTerm : query.expandedTerms()) {
            ScalaSupport.foreachParMap(expandedTerm.term().filePositionMap(), new Consumer<Tuple2<Object, int[]>>() {
                @Override
                public void accept(Tuple2<Object, int[]> e) {
                    retrievalDocuments.add(new RetrievalDocument((int) e._1(), e._2().length * expandedTerm.weight()));
                }
            });
        }
        RetrievalDocument[] docs = new RetrievalDocument[retrievalDocuments.size()];
        docs = retrievalDocuments.toArray(docs);
        Arrays.sort(docs);
        return SearchResultFactory.create(query, Arrays.asList(docs));
    }
}
