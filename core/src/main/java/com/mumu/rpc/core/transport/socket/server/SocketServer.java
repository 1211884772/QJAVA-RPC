package com.mumu.rpc.core.transport.socket.server;
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
import com.mumu.rpc.core.handler.RequestHandler;
import com.mumu.rpc.core.hook.ShutdownHook;
import com.mumu.rpc.core.provider.ServiceProvider;
import com.mumu.rpc.core.provider.ServiceProviderImpl;
import com.mumu.rpc.core.registry.NacosServiceRegistry;
import com.mumu.rpc.core.transport.RpcServer;
import com.mumu.rpc.core.registry.ServiceRegistry;
import com.mumu.rpc.core.serializer.CommonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Auther: mumu
 * @Date: 2022-09-14 18:48
 * @Description: com.mumu.rpc.core.server
 * @version:1.0
 */
public class SocketServer implements RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private final ExecutorService threadPool;//线程池
    private final String host;
    private final int port;
    private CommonSerializer serializer;
    private RequestHandler requestHandler = new RequestHandler();//请求助手
    //TimeUnit.SECONDS 时间单位

    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;

    public SocketServer(String host, int port) {
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
    }

    @Override
    public <T> void publishService(T service, Class<T> serviceClass) {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        serviceProvider.addServiceProvider(service, serviceClass);
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }

    @Override
    public void start() {
        //创建一个具有指定端口的服务器，侦听backlog和本地IP地址绑定
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(host, port));
            logger.info("服务器启动……");
            ShutdownHook.getShutdownHook().addClearAllHook();
            Socket socket;
            //侦听要连接到此套接字并接受它。
            while((socket = serverSocket.accept()) != null) {

                logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                //在将来某个时候执行给定的任务。
                //注册serviceRegistry=helloService服务
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serializer));
            }
            //线程池关闭
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }

}