package utils;



import java.util.Objects;

public class Config {
    private static final ThreadLocal<ConfigPOJO> CONFIG = ThreadLocal.withInitial(() ->{
        String path = "dataset/config.json";
        return LoadJSON.load(path, ConfigPOJO.class);
    });

    public static ConfigPOJO get(){
        return CONFIG.get();
    }

    public static void reset(){
        CONFIG.remove();
    }

    public static String environment(){
        return getValue("ENVIRONMENT", get().environment());
    }

    public static String entity(){
        return getValue("ENTITY", get().entity());
    }

    public static String browser(){
        return getValue("BROWSER", get().browser());
    }

    public static boolean remote(){
        return Boolean.parseBoolean(getValue("REMOTE", get().remote()));
    }

    public static String port(){
        return getValue("PORT", get().port());
    }

    public static String host(){
        return getValue("HOST", get().host());
    }

    public static int retry(){
        return Integer.parseInt(getValue("RETRY", get().retry()));
    }



    public static String getValue(String key,String defaultValue){
        String keyUpperCase = key.toUpperCase();
        if(Objects.isNull(System.getProperty(keyUpperCase)) || System.getProperty(keyUpperCase).trim().isEmpty()){
            return defaultValue;
        }else{
            return System.getProperty(keyUpperCase);
        }
    }
}
