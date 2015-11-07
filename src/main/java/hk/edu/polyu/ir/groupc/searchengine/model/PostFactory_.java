package hk.edu.polyu.ir.groupc.searchengine.model;

import java.io.*;
import java.util.stream.Stream;

/**
 * Created by beenotung on 11/7/15.
 */
public class PostFactory_ {
    @Deprecated
    static Stream<Post> loadFromFile(File file) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)))
                .lines().map(s -> PostFactory.createFromString(s));
    }
}
