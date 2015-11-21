package hk.edu.polyu.ir.groupc.searchengine.model.query;

import hk.edu.polyu.ir.groupc.searchengine.model.result.RetrievalDocument;

import java.util.List;

/**
 * Created by beenotung on 10/23/15.
 */
public abstract class RetrievalModel {
    {
        setMode(getDefaultMode());
    }

    public abstract List<String> getModes();

    public abstract String getDefaultMode();

    public abstract String getMode();

    public final void setMode(int index) {
        setMode(getModes().get(index));
    }

    public abstract void setMode(String newMode);

    public abstract List<Parameter> getParameters();

    public abstract List<RetrievalDocument> search(Query query);

    public class Parameter<T> {
        public final T min;
        public final T max;
        public final T suggested;
        /*for UI, and report*/
        public final String name;
        public T value;

        public Parameter(String name, T min, T max, T suggested) {
            this.name = name;
            this.min = min;
            this.max = max;
            this.suggested = suggested;
            value = suggested;
        }

    }
}
