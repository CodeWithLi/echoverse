package echoverse.common.utils;

import echoverse.common.exception.BaseException;

/**
 * 异常工具类
 */
public class ThrowUtils {

    /**
     * 条件成立时抛异常
     * @param result
     * @param message
     */
    public static void throwIf(boolean result,String message){
        if (result){
            throw new BaseException(message);
        }
    }
}
