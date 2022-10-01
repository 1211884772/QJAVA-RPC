package com.mumu.rpc.core.server;
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


import com.mumu.rpc.core.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Auther: mumu
 * @Date: 2022-09-14 18:48
 * @Description: com.mumu.rpc.core.server
 * @version:1.0
 */
public class RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);


    private static final int CORE_POOL_SIZE = 5;//核心线程数
    private static final int MAXIMUM_POOL_SIZE = 50;//最大线程数
    private static final int KEEP_ALIVE_TIME = 60;//线程空闲时间
    private static final int BLOCKING_QUEUE_CAPACITY = 100;//任务队列大小
    private final ExecutorService threadPool;//线程池
    private RequestHandler requestHandler = new RequestHandler();//请求助手
    private final ServiceRegistry serviceRegistry;
    //TimeUnit.SECONDS 时间单位

    public RpcServer(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        //工作队列
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //线程池
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    public void start(int port) {
        //创建一个具有指定端口的服务器，侦听backlog和本地IP地址绑定
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器启动……");
            Socket socket;
            //侦听要连接到此套接字并接受它。
            while((socket = serverSocket.accept()) != null) {

                logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                //在将来某个时候执行给定的任务。
                //注册serviceRegistry=helloService服务
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry));
            }
            //线程池关闭
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }
    }

}