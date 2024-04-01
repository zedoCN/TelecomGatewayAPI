package top.zedo.telecomcgi.info;

import com.google.gson.*;
import top.zedo.telecomcgi.GsonManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PMDisplay implements JsonDeserializer<PMDisplay> {
    /**
     * 规则数
     */
    public int count;
    /**
     * 网关ip
     */
    public String lanIp;
    /**
     * 子网掩码
     */
    public String mask;
    /**
     * 端口映射规则列表
     */
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
}
