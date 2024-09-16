package top.zedo.gatewayapi.info

import com.google.gson.*
import top.zedo.gatewayapi.GsonManager
import java.lang.reflect.Type

data class AllInfo(
    /**
     * 无线上行速度
     */
    var tWlUp: Long = 0,


    /**
     * 无线下行速度
     */
    var tWlDown: Long = 0,

    /**
     * 有线上行速度
     */
    var tWUp: Long = 0,

    /**
     * 有线下行速度
     */
    var tWDown: Long = 0,

    /**
     * 联网时间
     */
    var wanUpTime: Long = 0,

    /**
     * 存储设备数
     */
    var scount: Int = 0,

    /**
     * 电话业务
     */
    var voip: Boolean = false,

    /**
     * 广域网连接状态 可能是"CONNECTED" 注: 判断得转全大写
     */
    var wanConnect: String? = null,

    /**
     * 有线设备数
     */
    var wcount: Int = 0,

    /**
     * 无线设备数
     */
    var wlcount: Int = 0,

    /**
     * iTV业务
     */
    var itv: Boolean = false,

    /**
     * 有线设备列表
     */
    var pc: MutableList<deviceInfo> = ArrayList<deviceInfo>(),

    /**
     * 无线设备列表
     */
    var wifi: MutableList<deviceInfo> = ArrayList<deviceInfo>()
) : JsonDeserializer<AllInfo?> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): AllInfo {
        val allInfo = Gson().fromJson<AllInfo>(json, AllInfo::class.java)
        val jo = json.getAsJsonObject()
        for (i in 0 until allInfo.wcount)
            allInfo.pc.add(GsonManager.fromJson<deviceInfo>(jo.get("pc" + (i + 1)), deviceInfo::class.java))
        for (i in 0 until allInfo.wlcount)
            allInfo.wifi.add(GsonManager.fromJson<deviceInfo>(jo.get("wifi" + (i + 1)), deviceInfo::class.java))
        return allInfo
    }
}
