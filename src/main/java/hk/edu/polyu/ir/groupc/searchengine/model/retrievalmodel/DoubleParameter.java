package hk.edu.polyu.ir.groupc.searchengine.model.retrievalmodel;

import hk.edu.polyu.ir.groupc.searchengine.Debug;

/**
 * Created by beenotung on 11/23/15.
 */
public class DoubleParameter extends Parameter<Double> {
    public DoubleParameter(String name, Double min, Double max, Double suggested) {
        super(name, min, max, suggested);
    }

    public DoubleParameter(String name, Double min, Double max, Double suggested, Double value) {
        super(name, min, max, suggested, value);
    }

    @Override
    public void value(String newValue) {
        try {
            value(Double.parseDouble(newValue));
            Debug.logDone("set " + name.getValueSafe());
        } catch (NumberFormatException e) {
            Debug.loge_("Invalid Format");
        }
    }
}
