package com.mumu.rpc.core.socket.server;
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
import com.mumu.rpc.core.RequestHandler;
import com.mumu.rpc.core.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 处理RpcRequest的工作线程
 * @Auther: mumu
 * @Date: 2022-10-21 12:39
 * @Description: com.mumu.rpc.core.socket.server
 * @version:1.0
 */
public class RequestHandlerThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void run() {
        //socket.getInputStream()返回此套接字的输入流。
        //socket.getOutputStream()返回此套接字的输出流。
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            //readObject 从ObjectInputStream读取一个对象。
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            String interfaceName = rpcRequest.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handle(rpcRequest, service);
            //writeObject将指定的对象写入ObjectOutputStream。
            objectOutputStream.writeObject(RpcResponse.success(result));
            //flush 刷新流。
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用或发送时有错误发生：", e);
        }
    }

}
