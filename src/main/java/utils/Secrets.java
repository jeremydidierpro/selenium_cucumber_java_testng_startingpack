package utils;

import io.github.cdimascio.dotenv.Dotenv;

public final class Secrets {
    private static final Dotenv dotenv = Dotenv.configure()
            .filename(".env." + Config.environment())
            .ignoreIfMissing()
            .load();

    private Secrets(){}

    public static String apiUser() {
        return dotenv.get("API_USER");
    }

    public static String apiPassword() {
        return dotenv.get("API_PASSWORD");
    }

    public static String sqlServer() {
        return dotenv.get("SQL_SERVER");
    }

    public static String sqlPassword() {
        return dotenv.get("SQL_PASSWORD");
    }
}
