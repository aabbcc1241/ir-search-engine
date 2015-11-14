package hk.edu.polyu.ir.groupc.searchengine.model.query;

import comm.lang.ScalaSupport;
import hk.edu.polyu.ir.groupc.searchengine.model.Index;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.SearchResult;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.SearchResultFactory;
import scala.Option;
import scala.Tuple2;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.ListBuffer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by beenotung on 11/12/15.
 */
public class DummyModel extends RetrievalModel {
    @Override
    public SearchResult search(Query query) {
        List<RetrievalDocument> retrievalDocuments = new ArrayList<>();
        for (ExpandedTerm expandedTerm : query.expandedQuery()) {
            Option<HashMap<Object, ListBuffer<Object>>> option = Index.getFilePositionMap(expandedTerm.term());
            if (option.isEmpty()) continue;
            HashMap<Object, ListBuffer<Object>> filePositionMap = option.get();
            ScalaSupport.foreachMap(filePositionMap, new Consumer<Tuple2<Object, ListBuffer<Object>>>() {
                @Override
                public void accept(Tuple2<Object, ListBuffer<Object>> e) {
                    retrievalDocuments.add(new RetrievalDocument((int) e._1(), e._2().length()));
                }
            });
//            filePositionMap.foreach(e ->
//                    retrievalDocuments.add(new RetrievalDocument((int) e._1(), e._2().length())));
        }
        return SearchResultFactory.create(query, retrievalDocuments);
    }
}
