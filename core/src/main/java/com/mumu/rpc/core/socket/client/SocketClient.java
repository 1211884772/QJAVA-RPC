package com.mumu.rpc.core.socket.client;
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
import com.mumu.rpc.common.enumeration.RpcError;
import com.mumu.rpc.common.exception.RpcException;
import com.mumu.rpc.core.RpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Socket方式远程方法调用的消费者（客户端）
 * @Auther: mumu
 * @Date: 2022-09-14 18:56
 * @Description: com.mumu.rpc.core.client
 * @version:1.0
 */
public class SocketClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private final String host;
    private final int port;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    //发送socket请求
    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        //创建Socket
        try (Socket socket = new Socket(host, port)) {
            //socket.getInputStream()返回此套接字的输入流。
            //socket.getOutputStream()返回此套接字的输出流。
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            //writeObject将指定的对象写入ObjectOutputStream。
            objectOutputStream.writeObject(rpcRequest);
            //flush 刷新流。
            objectOutputStream.flush();
            //readObject 从ObjectInputStream读取一个对象。
            RpcResponse rpcResponse = (RpcResponse) objectInputStream.readObject();
            if (rpcResponse == null) {
                logger.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            if (rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            return rpcResponse.getData();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用时有错误发生：", e);
            throw new RpcException("服务调用失败: ", e);
        }
    }

}