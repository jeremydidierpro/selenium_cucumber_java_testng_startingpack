package utils;



public class Environment {
    private static final ThreadLocal<EnvironmentPOJO> ENV = ThreadLocal.withInitial(() -> {
        String path = "dataset/environment/%s.json".formatted(Config.environment());
        return LoadJSON.load(path, EnvironmentPOJO.class);
    });

    public static EnvironmentPOJO get(){
        return ENV.get();
    }

    public static void unload(){
        ENV.remove();
    }

    public static String baseUrl(){
        return get().getBaseUrl();
    }

    public static String apiUrl(){
        return get().getApiUrl();
    }

}
