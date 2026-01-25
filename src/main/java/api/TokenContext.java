package api;

import java.util.concurrent.locks.ReentrantLock;

public final class TokenContext {

    private static volatile String accessToken;
    private static final ReentrantLock refreshLock = new ReentrantLock();

    private TokenContext() {}

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String token) {
        accessToken = token;
    }


    public static ReentrantLock refreshLock() {
        return refreshLock;
    }
}