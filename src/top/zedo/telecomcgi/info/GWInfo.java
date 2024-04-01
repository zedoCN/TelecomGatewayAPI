
package top.zedo.telecomcgi.info;

public class GWInfo {
    public String DevType;
    public boolean wifiOnOff;
    public String LANIP;
    public String LANIPv6;
    public String MAC;
    public String ProductCls;
    public String ProductSN;
    public String SWVer;
    public String Vendor;
    public String WANIP;
    public String WANIPv6;
    public String ssid2g;
    public String ssid5g;
    public String wanAcnt;

    @Override
    public String toString() {
        return "GWInfo{" +
                "DevType='" + DevType + '\'' +
                ", wifiOnOff=" + wifiOnOff +
                ", LANIP='" + LANIP + '\'' +
                ", LANIPv6='" + LANIPv6 + '\'' +
                ", MAC='" + MAC + '\'' +
                ", ProductCls='" + ProductCls + '\'' +
                ", ProductSN='" + ProductSN + '\'' +
                ", SWVer='" + SWVer + '\'' +
                ", Vendor='" + Vendor + '\'' +
                ", WANIP='" + WANIP + '\'' +
                ", WANIPv6='" + WANIPv6 + '\'' +
                ", ssid2g='" + ssid2g + '\'' +
                ", ssid5g='" + ssid5g + '\'' +
                ", wanAcnt='" + wanAcnt + '\'' +
                '}';
    }
}
