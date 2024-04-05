package top.zedo.gatewayapi.info;

import com.google.gson.*;
import top.zedo.gatewayapi.GsonManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AllInfo implements JsonDeserializer<AllInfo> {
    /**
     * 无线上行速度
     */
    public long tWlUp;
    /**
     * 无线下行速度
     */
    public long tWlDown;

    /**
     * 有线上行速度
     */
    public long tWUp;
    /**
     * 有线下行速度
     */
    public long tWDown;
    /**
     * 联网时间
     */
    public long wanUpTime;
    /**
     * 存储设备数
     */
    public int scount;
    /**
     * 电话业务
     */
    public boolean voip;
    /**
     * 广域网连接状态 可能是"CONNECTED" 注: 判断得转全大写
     */
    public String wanConnect;
    /**
     * 有线设备数
     */
    public int wcount;
    /**
     * 无线设备数
     */
    public int wlcount;
    /**
     * iTV业务
     */
    public boolean itv;
    /**
     * 有线设备列表
     */
    public List<deviceInfo> pc;
    /**
     * 无线设备列表
     */
    public List<deviceInfo> wifi;


    @Override
    public AllInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        AllInfo allInfo = new Gson().fromJson(json, AllInfo.class);
        JsonObject jsonObject = json.getAsJsonObject();
        allInfo.pc = new ArrayList<>();
        for (int i = 0; i < allInfo.wcount; i++) {
            allInfo.pc.add(GsonManager.fromJson(jsonObject.get("pc" + (i + 1)), deviceInfo.class));
        }

        allInfo.wifi = new ArrayList<>();
        for (int i = 0; i < allInfo.wlcount; i++) {
            allInfo.wifi.add(GsonManager.fromJson(jsonObject.get("wifi" + (i + 1)), deviceInfo.class));
        }
        return allInfo;
    }
}
