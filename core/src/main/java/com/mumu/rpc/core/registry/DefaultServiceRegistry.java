package com.mumu.rpc.core.registry;
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


import com.mumu.rpc.common.enumeration.RpcError;
import com.mumu.rpc.common.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的服务注册表
 * @Auther: mumu
 * @Date: 2022-10-01 18:16
 * @Description: com.mumu.rpc.core.registry
 * @version:1.0
 */
public class DefaultServiceRegistry implements ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceRegistry.class);

    //创建一个新的，空的地图与默认的初始表大小（16）。
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    //创建一个新的Set支持的ConcurrentHashMap从给定的类型到Boolean.TRUE 。
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();
    /**
     * 注册服务
     * @param service 待注册的服务实体
     * @param <T>
     */
    @Override
    public synchronized <T> void register(T service) {
        //返回 Java 语言规范定义的底层类的规范名称
        String serviceName = service.getClass().getCanonicalName();
        //Java 集合类中的 Set.contains() 方法判断 Set 集合是否包含指定的对象。该方法返回值为 boolean 类型，如果 Set 集合包含指定的对象，则返回 true，否则返回 false。
        if(registeredService.contains(serviceName)) return;
        registeredService.add(serviceName);
        //获得service对象所实现的所有接口
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if(interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for(Class<?> i : interfaces) {
            //i.getCanonicalName()=com.mumu.rpc.api.HelloService
            //service=HelloServiceImpl@542
            serviceMap.put(i.getCanonicalName(), service);
        }
        logger.info("向接口: {} 注册服务: {}", interfaces, serviceName);
    }

    /**
     * 获取服务
     * @param serviceName 服务名称
     * @return
     */
    @Override
    public synchronized Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
