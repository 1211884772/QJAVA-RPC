package com.mmumu.rpc.server;
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


import com.mumu.rpc.api.HelloService;
import com.mumu.rpc.core.registry.DefaultServiceRegistry;
import com.mumu.rpc.core.registry.ServiceRegistry;
import com.mumu.rpc.core.server.RpcServer;
/**
 * @Auther: mumu
 * @Date: 2022-09-14 19:08
 * @Description: com.mmumu.rpc.server
 * @version:1.0
 */
public class TestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();

        //默认服务注册
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        //注册helloService服务
        serviceRegistry.register(helloService);
        //实例化服务端socket类
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }

}
