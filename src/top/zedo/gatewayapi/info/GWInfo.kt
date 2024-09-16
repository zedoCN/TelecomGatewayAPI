package top.zedo.gatewayapi.info

data class GWInfo(
    /**
     * 设备类型
     */
    var DevType: String? = null,
    /**
     * wifi开关状态
     */
    var wifiOnOff: Boolean = false,
    var LANIP: String? = null,
    var LANIPv6: String? = null,
    var MAC: String? = null,
    /**
     * 设备型号
     */
    var ProductCls: String? = null,

    /**
     * 序列号
     */
    var ProductSN: String? = null,
    /**
     * 版本号
     */
    var SWVer: String? = null,
    /**
     * 厂商
     */
    var Vendor: String? = null,
    /**
     * 广域网IP
     */
    var WANIP: String? = null,

    /**
     * 广域网IPv6
     */
    var WANIPv6: String? = null,
    var ssid2g: String? = null,
    var ssid5g: String? = null,

    /**
     * 上网账号
     */
    var wanAcnt: String? = null,
)
