package com.mumu.rpc.core.transport;
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
import com.mumu.rpc.core.serializer.CommonSerializer;

/**
 * 客户端类通用接口
 * @Auther: mumu
 * @Date: 2022-10-21 11:30
 * @Description: com.mumu.rpc.core
 * @version:1.0
 */
public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);
    void setSerializer(CommonSerializer serializer);
}
