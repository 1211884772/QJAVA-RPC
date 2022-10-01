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

    private final ExecutorService threadPool;
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    //构造方法
    public RpcServer() {
        int corePoolSize = 5;//核心线程数
        int maximumPoolSize = 50;//最大线程数
        long keepAliveTime = 60;//线程空闲时间
        //TimeUnit.SECONDS 时间单位
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);////任务队列
        ThreadFactory threadFactory = Executors.defaultThreadFactory();//线程工厂
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    //注册服务
    public void register(Object service, int port) {
        //创建一个具有指定端口的服务器，侦听backlog和本地IP地址绑定
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器正在启动...");
            Socket socket;
            //侦听要连接到此套接字并接受它。
            while((socket = serverSocket.accept()) != null) {
                logger.info("客户端连接！Ip为：" + socket.getInetAddress());//返回此服务器套接字的本地地址。
                //在将来某个时候执行给定的任务。
                //注册service=helloService服务
                threadPool.execute(new WorkerThread(socket, service));
            }
        } catch (IOException e) {
            logger.error("连接时有错误发生：", e);
        }
    }

}