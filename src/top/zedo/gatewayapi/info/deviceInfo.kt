// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation
package top.zedo.gatewayapi.info

data class deviceInfo (
    /**
     * 上行速度
     */
    var upSpeed: Long = 0,
    /**
     * ip地址
     */
    var ip: String? = null,
    /**
     * 在线时长
     */
    var onlineTime: Long = 0,
    /**
     * 型号
     */
    var model: String? = null,
    /**
     * 下行速度
     */
    var downSpeed: Long = 0,

    /**
     * 设备名称
     */
    var devName: String? = null,
    /**
     * 类型
     */
    var type: String? = null,
    /**
     * 厂商
     */
    var brand: String? = null

)