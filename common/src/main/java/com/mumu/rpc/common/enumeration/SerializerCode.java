package com.mumu.rpc.common.enumeration;
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


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字节流中的表示序列化和反序列化
 * @Auther: mumu
 * @Date: 2022-10-21 11:11
 * @Description: com.mumu.rpc.common.enumeration
 * @version:1.0
 */
@AllArgsConstructor
@Getter
public enum SerializerCode {
    KRYO(0),
    JSON(1),
    HESSIAN(2);

    private final int code;
}
