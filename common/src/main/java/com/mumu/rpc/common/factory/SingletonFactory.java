package com.mumu.rpc.common.factory;
//
//                       .::::.
//                     .::::::::.
//                    :::::::::::
//                 ..:::::::::::'
//              '::::::::::::'
//                .::::::::::
//           '::::::::::::::..
//                ..::::::::::::.
//              ``::::::::::::::::
//               ::::``:::::::::'        .:::.
//              ::::'   ':::::'       .::::::::.
//            .::::'      ::::     .:::::::'::::.
//           .:::'       :::::  .:::::::::' ':::::.
//          .::'        :::::.:::::::::'      ':::::.
//         .::'         ::::::::::::::'         ``::::.
//     ...:::           ::::::::::::'              ``::.
//    ```` ':.          ':::::::::'                  ::::..
//                       '.:::::'                    ':'````..
//
//
//
//                  年少太轻狂，误入码农行。
//                  白发森森立，两眼直茫茫。
//                  语言数十种，无一称擅长。
//                  三十而立时，无房单身郎。
//
//


import java.util.HashMap;
import java.util.Map;

/**
 * 单例工厂
 * @Auther: mumu
 * @Date: 2022-10-24 16:16
 * @Description: com.mumu.rpc.common.factory
 * @version:1.0
 */
public class SingletonFactory {

    private static Map<Class, Object> objectMap = new HashMap<>();

    private SingletonFactory() {}

    public static <T> T getInstance(Class<T> clazz) {
        Object instance = objectMap.get(clazz);
        synchronized (clazz) {
            if(instance == null) {
                try {
                    instance = clazz.newInstance();
                    objectMap.put(clazz, instance);
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        return clazz.cast(instance);
    }

}
