package com.mumu.rpc.client;
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


import com.mumu.rpc.api.HelloObject;
import com.mumu.rpc.api.HelloService;
import com.mumu.rpc.core.serializer.CommonSerializer;
import com.mumu.rpc.core.transport.RpcClientProxy;
import com.mumu.rpc.core.transport.socket.client.SocketClient;

/**
 * @Auther: mumu
 * @Date: 2022-09-14 19:19
 * @Description: com.mumu.rpc.client
 * @version:1.0
 */
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient(CommonSerializer.KRYO_SERIALIZER);
        RpcClientProxy proxy = new RpcClientProxy(client);
        //要代理的真实对象HelloService.class
        HelloService helloService = proxy.getProxy(HelloService.class);
        //传递的参数
        HelloObject object = new HelloObject(12, "This is a message");
        //代理请求服务端的helloService.hello(object)
        for(int i = 0; i < 20; i ++) {
            String res = helloService.hello(object);
            System.out.println(res);
        }
    }

}
