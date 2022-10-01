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
import com.mumu.rpc.core.client.RpcClientProxy;

/**
 * @Auther: mumu
 * @Date: 2022-09-14 19:19
 * @Description: com.mumu.rpc.client
 * @version:1.0
 */
public class TestClient {

    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        //要代理的真实对象HelloService.class
        HelloService proxyHelloService = proxy.getProxy(HelloService.class);
        //传递的参数
        HelloObject object = new HelloObject(12, "This is a message");

        //代理请求服务端的helloService.hello(object)
        String res = proxyHelloService.hello(object);
        System.out.println(res);
    }

}