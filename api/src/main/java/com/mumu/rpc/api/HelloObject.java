package com.mumu.rpc.api;
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
import lombok.Data;
import java.io.Serializable;
/**
 * 测试用api的实体
 * @Auther: mumu
 * @Date: 2022-09-14 18:59
 * @Description: com.mumu.rpc.api
 * @version:1.0
 */
@Data
@AllArgsConstructor
public class HelloObject implements  Serializable {

    private Integer id;
    private String message;

}
