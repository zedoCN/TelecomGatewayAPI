package top.zedo.telecomcgi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import top.zedo.telecomcgi.info.AllInfo;
import top.zedo.telecomcgi.info.PMDisplay;

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
                .registerTypeAdapter(PMDisplay.class, new PMDisplay())
                .registerTypeAdapter(AllInfo.class, new AllInfo())
                .setPrettyPrinting()
                .create();
    }
}
