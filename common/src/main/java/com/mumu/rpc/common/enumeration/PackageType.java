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
 * 包类型
 * @Auther: mumu
 * @Date: 2022-10-21 10:54
 * @Description: com.mumu.rpc.common.enumeration
 * @version:1.0
 */
@AllArgsConstructor
@Getter
public enum PackageType {
    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;
}
