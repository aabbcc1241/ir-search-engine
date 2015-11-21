package hk.edu.polyu.ir.groupc.searchengine.model;

import hk.edu.polyu.ir.groupc.searchengine.model.query.RetrievalModel;

/**
 * Created by beenotung on 11/21/15.
 */
public class ModelSetting {
    public RetrievalModel model;
    public String customDescription;
    public int numberOfRetrieval;

    public ModelSetting(RetrievalModel model, String customDescription, int numberOfRetrieval) {
        this.model = model;
        this.customDescription = customDescription;
        this.numberOfRetrieval = numberOfRetrieval;
    }

    public String getResultFilePath() {
        return "res\\result\\result-" + model.getClass().getSimpleName() + "-" + customDescription + "-" + numberOfRetrieval + ".txt";
    }

    public String getEvalFilePath() {
        return "res\\result\\eval-" + model.getClass().getSimpleName() + "-" + customDescription + "-" + numberOfRetrieval + ".txt";
    }
}
