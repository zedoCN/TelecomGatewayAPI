package top.zedo.gatewayapi.info

import com.google.gson.*
import top.zedo.gatewayapi.GsonManager
import top.zedo.gatewayapi.Protocol
import java.lang.reflect.Type

/**
 * @param client 目标Ip地址
 * @param desp 名称
 * @param enable 使能
 * @param exPort 外部端口
 * @param inPort  内部端口
 * @param protocol 协议
 */
data class PMRule(
    val client: String,
    val desp: String,
    val enable: Int,
    val exPort: Int,
    val inPort: Int,
    val protocol: String,
) {
    fun getProtocol() = Protocol.valueOf(protocol)
}


/**
 * @param count 规则数
 * @param lanIp 网关ip
 * @param mask 子网掩码
 * @param pmRule 端口映射规则列表
 */
data class PMRules(
    val count: Int = 0,
    val lanIp: String? = null,
    val mask: String? = null,
    val pmRule: MutableList<PMRule> = ArrayList<PMRule>()
) : JsonDeserializer<PMRules?> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): PMRules {
        val pmRules = Gson().fromJson<PMRules>(json, PMRules::class.java)
        val jsonObject = json.getAsJsonObject()
        for (i in 0 until pmRules.count) {
            pmRules.pmRule.add(GsonManager.fromJson<PMRule?>(jsonObject.get("pmRule" + (i + 1)), PMRule::class.java))
        }
        return pmRules
    }
}
