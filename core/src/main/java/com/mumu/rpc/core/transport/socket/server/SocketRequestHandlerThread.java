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




import com.mumu.rpc.common.entity.RpcRequest;
import com.mumu.rpc.common.entity.RpcResponse;
import com.mumu.rpc.core.handler.RequestHandler;
import com.mumu.rpc.core.registry.ServiceRegistry;
import com.mumu.rpc.core.serializer.CommonSerializer;
import com.mumu.rpc.core.transport.socket.util.ObjectReader;
import com.mumu.rpc.core.transport.socket.util.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * 处理RpcRequest的工作线程
 * @Auther: mumu
 * @Date: 2022-10-21 12:39
 * @Description: com.mumu.rpc.core.socket.server
 * @version:1.0
 */
public class SocketRequestHandlerThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SocketRequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private CommonSerializer serializer;

    public SocketRequestHandlerThread(Socket socket, RequestHandler requestHandler,CommonSerializer serializer) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serializer = serializer;
    }

    @Override
    public void run() {
        //socket.getInputStream()返回此套接字的输入流。
        //socket.getOutputStream()返回此套接字的输出流。
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            //readObject 从ObjectInputStream读取一个对象。
            RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(inputStream);
            Object result = requestHandler.handle(rpcRequest);

            RpcResponse<Object> response = RpcResponse.success(result, rpcRequest.getRequestId());
            //writeObject将指定的对象写入ObjectOutputStream。
            ObjectWriter.writeObject(outputStream, response, serializer);
        } catch (IOException e) {
            logger.error("调用或发送时有错误发生：", e);
        }
    }

}
