package hk.edu.polyu.ir.groupc.searchengine.model.retrievalmodel;

import hk.edu.polyu.ir.groupc.searchengine.model.query.Query;
import hk.edu.polyu.ir.groupc.searchengine.model.result.RetrievalDocument;

import java.util.List;

/**
 * Created by beenotung on 10/23/15.
 */
public abstract class RetrievalModel {
    {
        setMode(getDefaultMode());
    }

    @Override
    public final String toString() {
        return name();
    }

    public String name() {
        return getClass().getSimpleName();
    }

    public abstract List<String> getModes();

    public abstract String getDefaultMode();

    public abstract String getMode();

    public abstract void setMode(String newMode);

    public final void setMode(int index) {
        setMode(getModes().get(index));
    }

    public abstract List<Parameter<? extends Number>> getParameters();

    public abstract List<RetrievalDocument> search(Query query);

}
