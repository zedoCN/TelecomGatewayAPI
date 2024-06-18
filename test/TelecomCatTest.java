import top.zedo.gatewayapi.GsonManager;
import top.zedo.gatewayapi.TelecomGatewayAPI;

public class TelecomCatTest {
    public static void main(String[] args) {
        TelecomGatewayAPI telecomGatewayAPI = new TelecomGatewayAPI();
        telecomGatewayAPI.setHostIp("192.168.1.1");
        telecomGatewayAPI.login("useradmin", "xrvdp");
        System.out.println(GsonManager.toJson(telecomGatewayAPI.getPMRules()));
        System.out.println(GsonManager.toJson(telecomGatewayAPI.getAllInfo()));
        System.out.println(GsonManager.toJson(telecomGatewayAPI.getGWInfo()));
        System.out.println(GsonManager.toJson(telecomGatewayAPI.getGWStatus()));

    }
}
