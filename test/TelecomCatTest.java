import top.zedo.telecomcgi.GsonManager;
import top.zedo.telecomcgi.TelecomGatewayAPI;

public class TelecomCatTest {
    public static void main(String[] args) {
        TelecomGatewayAPI telecomGatewayAPI = new TelecomGatewayAPI();
        telecomGatewayAPI.login("useradmin", "xrvdp");
        System.out.println(GsonManager.toJson(telecomGatewayAPI.getPMDisplay()));
        System.out.println(GsonManager.toJson(telecomGatewayAPI.getAllInfo()));
        System.out.println(GsonManager.toJson(telecomGatewayAPI.getGWInfo()));
        System.out.println(GsonManager.toJson(telecomGatewayAPI.getGWStatus()));


    }
}
