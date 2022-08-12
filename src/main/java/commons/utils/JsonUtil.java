package commons.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonUtil {

    private static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    private JsonUtil() {
    }

    /**
     * 控制台输出 json 格式的对象
     *
     * @param object json 格式对象
     * @return 字符串类型的 json
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}
