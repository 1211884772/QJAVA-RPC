package com.mumu.rpc.core.registry;
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


import java.net.InetSocketAddress;

/**
 * 服务注册表通用接口
 * @Auther: mumu
 * @Date: 2022-10-01 18:18
 * @Description: com.mumu.rpc.core.registry
 * @version:1.0
 */
public interface ServiceRegistry {
    /**
     * 将一个服务注册进注册表
     *
     * @param serviceName 服务名称
     * @param inetSocketAddress 提供服务的地址
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * 根据服务名称查找服务实体
     *
     * @param serviceName 服务名称
     * @return 服务实体
     */
    InetSocketAddress lookupService(String serviceName);
}
