package com.mumu.rpc.core.codec;
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
import com.mumu.rpc.common.enumeration.PackageType;
import com.mumu.rpc.core.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Auther: mumu
 * @Date: 2022-10-21 11:48
 * @Description: com.mumu.rpc.core.codec
 * @version:1.0
 */
public class CommonEncoder extends MessageToByteEncoder {
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeInt(MAGIC_NUMBER);
        if(msg instanceof RpcRequest) {
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        out.writeInt(serializer.getCode());
        byte[] bytes = serializer.serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
