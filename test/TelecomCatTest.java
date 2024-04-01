import top.zedo.telecomcgi.GsonManager;
import top.zedo.telecomcgi.TelecomCat;

public class TelecomCatTest {
    public static void main(String[] args) {
        TelecomCat telecomCat = new TelecomCat();
        telecomCat.login("useradmin", "xrvdp");
        System.out.println(GsonManager.toJson(telecomCat.getPMDisplay()));
        System.out.println(GsonManager.toJson(telecomCat.getAllInfo()));
        System.out.println(GsonManager.toJson(telecomCat.getGWInfo()));
        System.out.println(GsonManager.toJson(telecomCat.getGWStatus()));
    }
}
