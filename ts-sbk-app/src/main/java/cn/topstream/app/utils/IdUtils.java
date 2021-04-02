package cn.topstream.app.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * ID 工具类
 *
 * @author RenYuLiang
 */
public final class IdUtils {


    /**
     * 禁止实例化
     */
    private IdUtils() {
        throw new AssertionError(">>>>>>>>类[IdUtils]禁止实例化<<<<<<<<");
    }

    /**
     * Twitter 的 Snowflake
     *
     * @return String
     */
    public static Long getSnowflakeId() {
        long id = RandomUtil.randomInt(2, 30);
        Snowflake snowflake = IdUtil.getSnowflake(id, id);
        return snowflake.nextId();
    }

}
