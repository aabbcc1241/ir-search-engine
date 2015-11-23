package hk.edu.polyu.ir.groupc.searchengine.model.retrievalmodel;

import hk.edu.polyu.ir.groupc.searchengine.Debug;

/**
 * Created by beenotung on 11/23/15.
 */
public class IntegerParameter extends Parameter<Integer> {
    public IntegerParameter(String name, Integer min, Integer max, Integer suggested) {
        super(name, min, max, suggested);
    }

    public IntegerParameter(String name, Integer min, Integer max, Integer suggested, Integer value) {
        super(name, min, max, suggested, value);
    }

    @Override
    public void value(String newValue) {
        try {
            value(Integer.parseInt(newValue));
            Debug.logDone("set " + name.getValueSafe());
        } catch (NumberFormatException e1) {
            try {
                value((int) Math.round(Double.parseDouble(newValue)));
                Debug.logBothStatus("rounded to " + value(), "set " + name.getValueSafe());
            } catch (NumberFormatException e2) {
                Debug.loge_("Invalid Format");
            }
        }
    }
}
