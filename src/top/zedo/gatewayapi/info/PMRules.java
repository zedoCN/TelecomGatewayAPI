package top.zedo.gatewayapi.info;

import com.google.gson.*;
import top.zedo.gatewayapi.GsonManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PMRules implements JsonDeserializer<PMRules> {
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
    public PMRules deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        PMRules pmRules = new Gson().fromJson(json, PMRules.class);
        JsonObject jsonObject = json.getAsJsonObject();
        pmRules.pmRule = new ArrayList<>();
        for (int i = 0; i < pmRules.count; i++) {
            pmRules.pmRule.add(GsonManager.fromJson(jsonObject.get("pmRule" + (i + 1)), PMRule.class));
        }
        return pmRules;
    }
}
