package com.mumu.rpc.core.transport.socket.client;
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
import com.mumu.rpc.common.util.RpcMessageChecker;
import com.mumu.rpc.core.registry.NacosServiceDiscovery;
import com.mumu.rpc.core.registry.NacosServiceRegistry;
import com.mumu.rpc.core.registry.ServiceDiscovery;
import com.mumu.rpc.core.registry.ServiceRegistry;
import com.mumu.rpc.core.transport.RpcClient;
import com.mumu.rpc.core.serializer.CommonSerializer;
import com.mumu.rpc.core.transport.socket.util.ObjectReader;
import com.mumu.rpc.core.transport.socket.util.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
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

    private final ServiceDiscovery serviceDiscovery;

    private final CommonSerializer serializer;

    public SocketClient() {
        this(DEFAULT_SERIALIZER);
    }
    public SocketClient(Integer serializer) {
        this.serviceDiscovery = new NacosServiceDiscovery();
        this.serializer = CommonSerializer.getByCode(serializer);
    }
    //发送socket请求
    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        //创建Socket
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            //socket.getInputStream()返回此套接字的输入流。
            //socket.getOutputStream()返回此套接字的输出流。
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            //writeObject将指定的对象写入ObjectOutputStream。
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
            //readObject 从ObjectInputStream读取一个对象。
            Object obj = ObjectReader.readObject(inputStream);
            RpcResponse rpcResponse = (RpcResponse) obj;

            if (rpcResponse == null) {
                logger.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            if (rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            RpcMessageChecker.check(rpcRequest, rpcResponse);
            return rpcResponse;
        } catch (IOException e) {
            logger.error("调用时有错误发生：", e);
            throw new RpcException("服务调用失败: ", e);
        }
    }

}
