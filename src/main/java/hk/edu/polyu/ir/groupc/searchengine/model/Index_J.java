package hk.edu.polyu.ir.groupc.searchengine.model;

import hk.edu.polyu.ir.groupc.searchengine.model.datasource.TermEntity;

/**
 * Created by beenotung on 11/11/15.
 * same as Index (scala object)
 * just for reference
 * no need to call function from this class even when you're calling from Java Class
 */
@Deprecated
public class Index_J {
    public static int getDocLen(int fileId) {
        return Index.getDocLen(fileId);
    }

    public static int getTF(TermEntity termEnity, int fileId) {
        return Index.getTF(termEnity, fileId);
    }

    @Deprecated
    public static int getTF(String term, int fileId) {
        return Index.getTF(term, fileId);
    }

    @Deprecated
    public static double getDF(String term, int fileId) {
        return Index.getDF(term, fileId);
    }

    public static double getDF(TermEntity termEntity, int fileId) {
        return Index.getDF(termEntity, fileId);
    }

    public static double getDocN() {
        return Index.getDocN();
    }

    @Deprecated
    public static double getTFIDF(String term, int fileId) {
        return Index.getTFIDF(term, fileId);
    }

    public static double getTFIDF(TermEntity termEntity, int fileId) {
        return Index.getTFIDF(termEntity, fileId);
    }

    public static double getIDF(String term, int fileId) {
        return Index.getIDF(term, fileId);
    }

    public static void getDocFile(int fileId) {
        Index.getDocFile(fileId);
    }
}
