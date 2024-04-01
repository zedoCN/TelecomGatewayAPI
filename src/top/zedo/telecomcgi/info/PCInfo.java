// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package top.zedo.telecomcgi.info;
import java.util.List;

public class PCInfo {
    public long upSpeed;
    public String ip;
    public long onlineTime;
    public String model;
    public long downSpeed;
    public String devName;
    public String type;
    public String brand;

    @Override
    public String toString() {
        return "PCInfo{" +
                "upSpeed=" + upSpeed +
                ", ip='" + ip + '\'' +
                ", onlineTime=" + onlineTime +
                ", model='" + model + '\'' +
                ", downSpeed=" + downSpeed +
                ", devName='" + devName + '\'' +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
