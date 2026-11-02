import top.zedo.gatewayapi.Protocol
import top.zedo.gatewayapi.TelecomGatewayAPI


object TelecomCatTest {
    @JvmStatic
    fun main(args: Array<String>) {

        TelecomGatewayAPI(
            requireNotNull(System.getenv("CTGW_USERNAME")) { "Set CTGW_USERNAME" },
            requireNotNull(System.getenv("CTGW_PASSWORD")) { "Set CTGW_PASSWORD" },
            System.getenv("CTGW_URL") ?: "http://192.168.1.1"
        ).run {
            login()

            //getPMRules().getOrThrow().pmRule.forEach { t -> t.log() }

            /*removePMRule("nginx")
            removePMRule("mattermostAU")
            removePMRule("mattermost7")
            removePMRule("mattermost4")
            removePMRule("mattermost5")
            removePMRule("Rocket.Chat")*/
            //addPMRule("nginx", "192.168.2.100", Protocol.BOTH, 6, 6)
            //addPMRule("Rocket.Chat", "192.168.2.100", Protocol.BOTH, 3000, 3000)
            //removePMRule("aio")
            //removePMRule("aio3")
            //removePMRule("aioD")
            //addPMRule("aio", "192.168.2.100", Protocol.BOTH, 8080, 8080)
            //addPMRule("aio3", "192.168.2.100", Protocol.BOTH, 8443, 8443)
            //addPMRule("aioD", "192.168.2.100", Protocol.BOTH, 3478, 3478)

            /*removePMRule("T80")
            removePMRule("T443")
            addPMRule("T80", "192.168.2.100", Protocol.TCP, 8080, 8000)
            addPMRule("T443", "192.168.2.100", Protocol.TCP, 8443, 8443)
            addPMRule("10000", "192.168.2.100", Protocol.BOTH, 10000, 10000)*//*addPMRule("mattermostAU", "192.168.2.100", Protocol.BOTH, 8443, 8443)
                addPMRule("mattermost7", "192.168.2.100", Protocol.BOTH, 8067, 8067)
                addPMRule("mattermost4", "192.168.2.100", Protocol.BOTH, 8074, 8074)
                addPMRule("mattermost5", "192.168.2.100", Protocol.BOTH, 8075, 8075)*/

            /*removePMRule("M47984")
            removePMRule("M47989")
            removePMRule("M48010")
            removePMRule("M47998")
            removePMRule("M47999")
            removePMRule("M48000")*/
//            removePMRule("VPN")
//            addPMRule("VPN", "192.168.2.100", Protocol.TCP, 10807, 10807)

            /*  addPMRule("M47984", "192.168.1.5", Protocol.TCP, 47984, 47984)
              addPMRule("M47989", "192.168.1.5", Protocol.TCP, 47989, 47989)
              addPMRule("M48010", "192.168.1.5", Protocol.TCP, 48010, 48010)
              addPMRule("M47998", "192.168.1.5", Protocol.UDP, 47998, 47998)
              addPMRule("M47999", "192.168.1.5", Protocol.UDP, 47999, 47999)
              addPMRule("M48000", "192.168.1.5", Protocol.UDP, 48000, 48000)*/


            /*addPMRule("S27031", "192.168.1.5", Protocol.UDP, 27031 , 27031 )
            addPMRule("S27036", "192.168.1.5", Protocol.BOTH, 27036, 27036)
            addPMRule("S27037", "192.168.1.5", Protocol.TCP, 27037, 27037)*/

//            removePMRule("pr-agent")
//            addPMRule("pr-agent", "192.168.1.2", Protocol.BOTH, 3001, 3001)

            /* removePMRule("MC")
             addPMRule("MC", "192.168.1.100", Protocol.BOTH, 25565, 25565)*/
            /*removePMRule("MC")
            addPMRule("MC", "192.168.1.2", Protocol.BOTH, 25565, 25565)*/

            /*removePMRule("Arch")
            addPMRule("Arch", "192.168.1.2", Protocol.TCP, 2222, 22)*/

            /*removePMRule("MC2")
            addPMRule("MC2", "192.168.1.5", Protocol.TCP, 25564, 1145)*//*removePMRule("Isekai Palworld Server")
                addPMRule("Isekai Palworld Server", "192.168.1.100", Protocol.BOTH, 8211, 8211)*/
//                removePMRule("Steam")


            /*removePMRule("Loli1")
            removePMRule("Loli2")
            removePMRule("Loli3")*//*addPMRule("Loli1", "192.168.1.100", Protocol.BOTH, 26900, 26900)
                addPMRule("Loli2", "192.168.1.100", Protocol.BOTH, 26901, 26901)
                addPMRule("Loli3", "192.168.1.100", Protocol.BOTH, 26902, 26902)*/

//                removePMRule("MCSVC")
//                addPMRule("MCSVC", "192.168.1.5", Protocol.UDP, 24454, 24454)
            //removePMRule("MC2")
            /*addPMRule("frp", "192.168.1.100", Protocol.BOTH, 8000, 8000)
            addPMRule("mc", "192.168.1.100", Protocol.BOTH, 25565, 25565)
            addPMRule("rdp-zedo", "192.168.1.5", Protocol.BOTH, 3289, 3389)*/

//            addPMRule("mc", "192.168.1.100", Protocol.BOTH, 25565, 25565)


//                removePMRule("mc")
//                removePMRule("mc")

//                addPMRule("mc", "192.168.1.100", Protocol.BOTH, 25565, 25565)
//                addPMRule("mc", "192.168.1.100", Protocol.BOTH, 25565, 25565)

            // --- 移除并重新添加所有端口映射规则 ---

           /* getPMRules().getOrThrow().pmRule.forEach { t -> removePMRule(t.desp) }

            addPMRule("M48000", "192.168.1.5", Protocol.UDP, 48000, 48000)
            addPMRule("S27031", "192.168.1.5", Protocol.UDP, 27031, 27031)
            addPMRule("S27036", "192.168.1.5", Protocol.BOTH, 27036, 27036)
            addPMRule("S27037", "192.168.1.5", Protocol.TCP, 27037, 27037)
            addPMRule("mc", "192.168.1.5", Protocol.BOTH, 25565, 25565)
            addPMRule("frp", "192.168.1.100", Protocol.BOTH, 8000, 8000)
            addPMRule("M47984", "192.168.1.5", Protocol.TCP, 47984, 47984)
            addPMRule("linker", "192.168.1.100", Protocol.BOTH, 5431, 5431)
            addPMRule("M48010", "192.168.1.5", Protocol.TCP, 48010, 48010)
            addPMRule("bitcomet", "192.168.1.100", Protocol.TCP, 22223, 22223)
            addPMRule("M47989", "192.168.1.5", Protocol.TCP, 47989, 47989)
            addPMRule("M47998", "192.168.1.5", Protocol.UDP, 47998, 47998)
            addPMRule("M47999", "192.168.1.5", Protocol.UDP, 47999, 47999)
            addPMRule("web", "192.168.1.100", Protocol.TCP, 408, 408)
            addPMRule("unityAccelerator", "192.168.1.100", Protocol.BOTH, 10081, 10081)
//            addPMRule("rdp-server", "192.168.1.100", Protocol.TCP, 3189, 3389)
            addPMRule("rdp-zedo", "192.168.1.5", Protocol.BOTH, 3289, 3389)
            addPMRule("cs2", "192.168.1.5", Protocol.UDP, 27015, 27015)


//            addPMRule("proxy", "192.168.1.100", Protocol.TCP, 10809, 10807)
*/
            getPMRules().getOrThrow().pmRule.forEach { t -> t.log() }

            getAllInfo().log()
            getGWStatus().log()

//            reboot()
            //getGWStatus().getOrThrow().cpuUsed.log()
            logout()
        }
    }
}

fun <T> T.log() = also { println(this) }
