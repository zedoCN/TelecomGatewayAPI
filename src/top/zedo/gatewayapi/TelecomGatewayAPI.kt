@file:Suppress("unused")

package top.zedo.gatewayapi

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import top.zedo.gatewayapi.info.AllInfo
import top.zedo.gatewayapi.info.GWInfo
import top.zedo.gatewayapi.info.GWStatus
import top.zedo.gatewayapi.info.PMRules

private val client = OkHttpClient.Builder()
    .followRedirects(false)
    .build()
private val gson = GsonBuilder()
    .registerTypeAdapter(PMRules::class.java, PMRules())
    .registerTypeAdapter(AllInfo::class.java, AllInfo())
    .setPrettyPrinting()
    .setLenient()
    .create()
private val pattern = ".+([a-z0-9]{32}).+".toRegex(RegexOption.DOT_MATCHES_ALL)

fun <T> fromJson(element: JsonElement, classOfT: Class<T>): T {
    return gson.fromJson(element, classOfT);
}


class TelecomGatewayAPI(
    val username: String,
    val password: String,
    var baseUrl: String = "http://192.168.1.1"
) {
    var cookie = ""
    var token = ""

    /**
     * 获取网关状态
     */
    fun getGWStatus() = requestGet<GWStatus>("/cgi-bin/luci/admin/settings/gwstatus")

    /**
     * 获取网关信息
     */
    fun getGWInfo() = requestGet<GWInfo>("/cgi-bin/luci/admin/settings/gwinfo?get=all")

    /**
     * 获取端口映射列表
     */
    fun getPMRules() = requestGet<PMRules>("/cgi-bin/luci/admin/settings/pmDisplay")

    /**
     * 获取所有信息
     */
    fun getAllInfo() = requestGet<AllInfo>("/cgi-bin/luci/admin/allInfo")


    /**
     * 增加端口映射规则
     *
     * @param name     规则名
     * @param ip       目标Ip地址
     * @param protocol 协议
     * @param exPort   外部端口
     * @param inPort   内部端口
     */
    fun addPMRule(name: String, ip: String, protocol: Protocol, exPort: Int, inPort: Int) =
        pmSetSingle("add", name, ip, protocol, exPort, inPort)

    /**
     * 启用端口映射规则
     *
     * @param name 规则名
     */
    fun enablePMRule(name: String) = pmSetSingle("enable", name)

    /**
     * 禁用端口映射规则
     *
     * @param name 规则名
     */
    fun disablePMRule(name: String) = pmSetSingle("disable", name)

    /**
     * 移除端口映射规则
     *
     * @param name 规则名
     */
    fun removePMRule(name: String) = pmSetSingle("del", name)


    /**
     * 移除所有端口映射规则
     */

    fun removeAllPMRules() = pmSetAll("del")

    /**
     * 禁用所有端口映射规则
     */

    fun disableAllPMRules() = pmSetAll("disable")

    /**
     * 启用所有端口映射规则
     */

    fun enableAllRules() = pmSetAll("enable")

    private fun pmSetAll(op: String) =
        requestPost<String>("/cgi-bin/luci/admin/settings/pmSetAll", FormBody.Builder().add("op", op))

    private fun pmSetSingle(
        op: String,
        srvname: String,
        client: String? = null,
        protocol: Protocol? = null,
        exPort: Int? = null,
        inPort: Int? = null,
    ) {
        val form = FormBody.Builder()
            .add("op", op)
            .add("srvname", srvname)
        if (client != null) form.add("client", client)
        if (protocol != null) form.add("protocol", protocol.name)
        if (exPort != null) form.add("exPort", exPort.toString())
        if (inPort != null) form.add("inPort", inPort.toString())
        requestPost<String>("/cgi-bin/luci/admin/settings/pmSetSingle", form)
    }

    /**
     * 登录
     */
    fun login() {
        val cook = requestInner(
            url = "/cgi-bin/luci",
            body = FormBody.Builder().add("username", username).add("psd", password)
        ) {
            check(it.code == 302) {
                println(it)
                "登陆失败"
            }
            it.header("Set-Cookie")!!.split(";")[0].split("=")[1]
        }.getOrThrow()
        cookie = "sysauth=$cook"
        token = requestGet<String>("/cgi-bin/luci").getOrThrow().let {
            pattern.matchAt1(it)!!
        }
    }

    /**
     * 登出
     */
    fun logout() = requestPost<String>("/cgi-bin/luci/admin/logout")

    /**
     * 重启
     */
    fun reboot() = requestPost<String>("/cgi-bin/luci/admin/reboot")

    private inline fun <reified R> requestGet(url: String): Result<R> {
        return requestInner(url, null) {
            val bodyString = it.body.string()
            check(it.code == 200) { bodyString }
            if (R::class.java == String::class.java) bodyString as R
            else gson.fromJson(bodyString, R::class.java)
        }
    }

    private inline fun <reified R> requestPost(url: String, body: FormBody.Builder = FormBody.Builder()): Result<R> {
        return requestInner(url, body) { response ->
            val bodyString = response.body.string()
            check(response.code == 200) { bodyString }
            if (R::class.java == String::class.java) bodyString as R
            else gson.fromJson(bodyString, R::class.java)
        }
    }

    private inline fun <reified R> requestInner(
        url: String,
        body: FormBody.Builder? = null,
        onResult: (Response) -> R
    ): Result<R> {
        if (token.isNotEmpty()) body?.add("token", token)
        val request = Request.Builder()
            .url("$baseUrl$url")
            .header("Cookie", cookie)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .let { if (body == null) it.get() else it.post(body.build()) }
            .build()
        return runCatching {
            client.newCall(request).execute().use(onResult)
        }
    }

}

private fun Regex.matchAt1(text: String) = matchEntire(text)?.groupValues?.getOrNull(1)

