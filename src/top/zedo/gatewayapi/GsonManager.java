package top.zedo.gatewayapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import top.zedo.gatewayapi.info.AllInfo;
import top.zedo.gatewayapi.info.PMRules;

public class GsonManager {
    private static final Gson gson;

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(JsonElement element, Class<T> classOfT) {
        return gson.fromJson(element, classOfT);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(PMRules.class, new PMRules())
                .registerTypeAdapter(AllInfo.class, new AllInfo())
                .setPrettyPrinting()
                .create();
    }
}
