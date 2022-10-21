package com.mumu.rpc.core.serializer;
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


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mumu.rpc.common.entity.RpcRequest;
import com.mumu.rpc.common.enumeration.SerializerCode;
import com.mumu.rpc.common.exception.SerializeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 使用JSON格式的序列化器
 * @Auther: mumu
 * @Date: 2022-10-21 11:57
 * @Description: com.mumu.rpc.core.serializer
 * @version:1.0
 */
public class JsonSerializer implements CommonSerializer {

    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            logger.error("序列化时有错误发生:", e);
            throw new SerializeException("序列化时有错误发生");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try {
            Object obj = objectMapper.readValue(bytes, clazz);
            if(obj instanceof RpcRequest) {
                obj = handleRequest(obj);
            }
            return obj;
        } catch (IOException e) {
            logger.error("序列化时有错误发生:", e);
            throw new SerializeException("序列化时有错误发生");
        }
    }

    /*
        这里由于使用JSON序列化和反序列化Object数组，无法保证反序列化后仍然为原实例类型
        需要重新判断处理
     */
    private Object handleRequest(Object obj) throws IOException {
        RpcRequest rpcRequest = (RpcRequest) obj;
        for(int i = 0; i < rpcRequest.getParamTypes().length; i ++) {
            Class<?> clazz = rpcRequest.getParamTypes()[i];
            if(!clazz.isAssignableFrom(rpcRequest.getParameters()[i].getClass())) {
                byte[] bytes = objectMapper.writeValueAsBytes(rpcRequest.getParameters()[i]);
                rpcRequest.getParameters()[i] = objectMapper.readValue(bytes, clazz);
            }
        }
        return rpcRequest;
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("JSON").getCode();
    }

}
