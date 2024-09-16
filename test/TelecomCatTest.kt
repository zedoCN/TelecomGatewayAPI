import top.zedo.gatewayapi.TelecomGatewayAPI


object TelecomCatTest {
    @JvmStatic
    fun main(args: Array<String>) {
//        TelecomGatewayAPI telecomGatewayAPI = new TelecomGatewayAPI();
//        telecomGatewayAPI.setHostIp("192.168.1.1");
//        while (true){
//            telecomGatewayAPI.login("useradmin", "xrvdp");
//            System.out.println(GsonManager.toJson(telecomGatewayAPI.getPMRules()));
//            System.out.println(GsonManager.toJson(telecomGatewayAPI.getAllInfo()));
//            System.out.println(GsonManager.toJson(telecomGatewayAPI.getGWInfo()));
//            System.out.println(GsonManager.toJson(telecomGatewayAPI.getGWStatus()));
//            telecomGatewayAPI.logout();
//        }


        TelecomGatewayAPI("useradmin", "xrvdp", "http://192.168.1.1").run {
            login()
            //getAllInfo().getOrThrow().pc?.forEach { t -> t.log() }
            getAllInfo().getOrThrow().wifi?.forEach { t -> t.log() }
            logout()
        }

        //        System.out.println(GsonManager.fromJson("awdwadw", String.class));
    }
}

fun <T> T.log() = also { println(this) }
