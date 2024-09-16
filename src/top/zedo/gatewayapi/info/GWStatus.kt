package top.zedo.gatewayapi.info

data class GWStatus(
    /**
     * cpu占用 范围:0-100
     */
    var cpuUsed: Int = 0,

    /**
     * iTV业务
     */
    var itv: Boolean = false,
    /**
     * 存储设备数
     */
    var scount: Int = 0,
    /**
     * 运行时间
     */
    var sysTime: Long = 0,
    /**
     * 上网时间
     */
    var upTime: Long = 0,

    /**
     * 电话业务
     */
    var voip: Boolean = false,
    /**
     * 有线设备数
     */
    var wcount: Int = 0,
    /**
     * 无线设备数
     */
    var wlcount: Int = 0
)