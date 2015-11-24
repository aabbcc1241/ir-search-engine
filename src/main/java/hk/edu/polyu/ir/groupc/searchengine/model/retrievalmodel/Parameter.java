package hk.edu.polyu.ir.groupc.searchengine.model.retrievalmodel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by beenotung on 11/23/15.
 */
public abstract class Parameter<T extends Number> {
    //        public final T min;
//        public final T max;
//        public final T suggested;
    public final SimpleObjectProperty<T> min;
    public final SimpleObjectProperty<T> max;
    public final SimpleObjectProperty<T> suggested;
    /*for UI, and report*/
//        public final String name;
    public final SimpleStringProperty name;
    //        public T value;
    public final SimpleObjectProperty<T> value;

    public Parameter(String name, T min, T max, T suggested) {
        this(name, min, max, suggested, suggested);
    }

    public Parameter(String name, T min, T max, T suggested, T value) {
        this.name = new SimpleStringProperty(name);
        this.min = new SimpleObjectProperty<T>(this, "min", min);
        this.max = new SimpleObjectProperty<T>(this, "max", max);
        this.suggested = new SimpleObjectProperty<T>(this, "suggested", suggested);
        this.value = new SimpleObjectProperty<T>(this, "value", value);
    }

    public void addListener() {
        value.addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observableValue, T oldValue, T newValue) {

            }
        });
    }

    public T value() {
        return value.getValue();
    }

    public T suggested() {
        return suggested.getValue();
    }

    public void value(T t) {
        value.setValue(t);
    }

    public abstract void value(String newValue);
}
