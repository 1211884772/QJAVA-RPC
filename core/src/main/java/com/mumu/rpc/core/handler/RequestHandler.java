package com.mumu.rpc.core.handler;
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


import com.mumu.rpc.common.entity.RpcRequest;
import com.mumu.rpc.common.entity.RpcResponse;
import com.mumu.rpc.common.enumeration.ResponseCode;
import com.mumu.rpc.core.provider.ServiceProvider;
import com.mumu.rpc.core.provider.ServiceProviderImpl;
import com.mumu.rpc.core.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 进行过程调用的处理器
 * @Auther: mumu
 * @Date: 2022-10-21 11:31
 * @Description: com.mumu.rpc.core
 * @version:1.0
 */
public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final ServiceProvider serviceProvider;

    static {
        serviceProvider = new ServiceProviderImpl();
    }

    public Object handle(RpcRequest rpcRequest) {
        Object result = null;
        Object service = serviceProvider.getServiceProvider(rpcRequest.getInterfaceName());
        try {
            //代理调用方法
            result = invokeTargetMethod(rpcRequest, service);
            logger.info("服务:{} 成功调用方法:{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("调用或发送时有错误发生：", e);
        }
        return result;
    }


    //代理调用方法
    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws IllegalAccessException, InvocationTargetException {
        Method method;
        try {
            //getClass 返回此 Object的运行时类
            //getMethod 返回一个 方法对象，它反映此表示的类或接口的指定公共成员方法 类对象。
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND, rpcRequest.getRequestId());
        }
        //invoke JAX-WS运行时调用此方法来对端点实例执行实际的Web服务调用。
        return method.invoke(service, rpcRequest.getParameters());
    }
}
