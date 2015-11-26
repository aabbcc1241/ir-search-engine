package hk.edu.polyu.ir.groupc.searchengine;

import comm.Utils;
import comm.gui.AlertUtils;
import hk.edu.polyu.ir.groupc.searchengine.frontend.MainController;
import hk.edu.polyu.ir.groupc.searchengine.model.retrievalmodel.*;

/**
 * Created by beenotung on 11/24/15.
 */
public class Config {
    public static void init() {
        /* setting default values */
        MainController.defaultNumOfRetrievalDocument_$eq(1000);
        /* add models */
//        MainController.addModel(new SimpleModel());
        MainController.addModel(new BooleanModel());
        MainController.addModel(new VectorSpaceModel());
        MainController.addModel(new SetBasedVectorSpaceModel());
        MainController.addModel(new ExtendedBooleanModel());
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5000);
//                    CheckSystemInfo();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }

    private static final int MIN_RAM_IN_G = 6;

    public static void CheckSystemInfo() {
        String action = "System checking";
//        Debug.logMainStatus(action);
        if (Runtime.getRuntime().totalMemory() < (1024L * 1024 * 1024 * MIN_RAM_IN_G))
            AlertUtils.warn("Not Enough Ram", "The program might run very slow", "Please give at least " + (1024 * MIN_RAM_IN_G) + "M RAM for the JVM.\nCurrent available RAM: " + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + "M");
//        else
//            AlertUtils.info("OK", "OK", (Runtime.getRuntime().totalMemory() / 1024 / 1024 + "M"));
        else
            Debug.logBothStatus("Ready", Utils.getRamUsageString());
    }
}
