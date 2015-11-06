package hk.edu.polyu.ir.groupc;

import scala.collection.Iterator;

import java.util.function.Consumer;

/**
 * Created by beenotung on 11/7/15.
 */
public class Utils {
    public static <E> void foreach(Iterator<E> iterator, Consumer<E> consumer) {
        while (iterator.hasNext())
            consumer.accept(iterator.next());
    }
}
