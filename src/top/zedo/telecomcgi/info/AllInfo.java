package top.zedo.telecomcgi.info;

import com.google.gson.*;
import top.zedo.telecomcgi.GsonManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AllInfo implements JsonDeserializer<AllInfo> {
    public long tWUp;
    public long tWDown;
    public long wanUpTime;
    public int scount;
    public boolean voip;
    public String wanConnect;
    public int wcount;
    public int wlcount;
    public boolean itv;
    public List<PCInfo> pc;

    @Override
    public String toString() {
        return "AllInfo{" +
                "tWUp=" + tWUp +
                ", tWDown=" + tWDown +
                ", wanUpTime=" + wanUpTime +
                ", scount=" + scount +
                ", voip=" + voip +
                ", wanConnect='" + wanConnect + '\'' +
                ", wcount=" + wcount +
                ", wlcount=" + wlcount +
                ", itv=" + itv +
                ", pc=" + pc +
                '}';
    }

    @Override
    public AllInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        AllInfo allInfo = new Gson().fromJson(json, AllInfo.class);
        JsonObject jsonObject = json.getAsJsonObject();
        allInfo.pc = new ArrayList<>();
        for (int i = 0; i < allInfo.wcount; i++) {
            allInfo.pc.add(GsonManager.fromJson(jsonObject.get("pc" + (i + 1)), PCInfo.class));
        }
        return allInfo;
    }
}
