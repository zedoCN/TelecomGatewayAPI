package top.zedo.telecomcgi.info;

import com.google.gson.*;
import top.zedo.telecomcgi.GsonManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PMDisplay implements JsonDeserializer<PMDisplay> {
    public int count;
    public String lanIp;
    public String mask;
    public List<PMRule> pmRule;

    @Override
    public PMDisplay deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        PMDisplay pmDisplay = new Gson().fromJson(json, PMDisplay.class);
        JsonObject jsonObject = json.getAsJsonObject();
        pmDisplay.pmRule = new ArrayList<>();
        for (int i = 0; i < pmDisplay.count; i++) {
            pmDisplay.pmRule.add(GsonManager.fromJson(jsonObject.get("pmRule" + (i + 1)), PMRule.class));
        }
        return pmDisplay;
    }

    @Override
    public String toString() {
        return "PMDisplay{" +
                "count=" + count +
                ", lanIp='" + lanIp + '\'' +
                ", mask='" + mask + '\'' +
                ", pmRule=" + pmRule +
                '}';
    }
}
