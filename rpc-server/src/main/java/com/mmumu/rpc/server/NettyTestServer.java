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
import com.mumu.rpc.core.netty.server.NettyServer;
import com.mumu.rpc.core.registry.DefaultServiceRegistry;
import com.mumu.rpc.core.registry.ServiceRegistry;
import com.mumu.rpc.core.serializer.KryoSerializer;

/**
 * 测试用Netty服务提供者（服务端）
 * @Auther: mumu
 * @Date: 2022-10-21 12:19
 * @Description: com.mmumu.rpc.server
 * @version:1.0
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.setSerializer(new KryoSerializer());
        server.start(9999);
    }
}
