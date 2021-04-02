package cn.topstream.app.utils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * SimpleDateFormat 享元模式
 *
 * @author RenYuLiang
 */
public class SimpleDateFormatFactory {

    /**
     * 禁止实例化
     */
    private SimpleDateFormatFactory() {
        throw new AssertionError(">>>>>>>>类[SimpleDateFormatFactory]禁止实例化<<<<<<<<");
    }

    private static final ThreadLocal<Map<String, SimpleDateFormat>> THREAD_LOCAL =
            ThreadLocal.withInitial(() -> new HashMap<>(16));

    public static SimpleDateFormat getInstance(final String format) {
        SimpleDateFormat sdf = THREAD_LOCAL.get().get(format);
        if (null == sdf) {
            sdf = new SimpleDateFormat(format);
            THREAD_LOCAL.get().put(format, sdf);
        }
        return sdf;
    }

}
