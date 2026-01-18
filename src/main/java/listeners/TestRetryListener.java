package listeners;


import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utils.Config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestRetryListener implements IRetryAnalyzer {

    private static final Map<String, Integer> attempts = new ConcurrentHashMap<>();
    private final int retryMax = Config.retry();

    @Override
    public boolean retry(ITestResult result) {
        String id = getTestId(result);

        int attempt = attempts.getOrDefault(id, 0);
        boolean willRetry = attempt < retryMax;

        result.setAttribute("RETRY_ATTEMPT", attempt + 1);     // 1-based for reporting
        result.setAttribute("WILL_RETRY", willRetry);

        if (willRetry) {
            attempts.put(id, attempt + 1);
            return true;
        }

        // cleanup when done (success or final failure)
        attempts.remove(id);
        return false;
    }

    public static void clear(ITestResult result) {
        attempts.remove(getTestId(result));
    }

    private static String getTestId(ITestResult result) {
        String cls = result.getTestClass().getName();
        String method = result.getMethod().getMethodName();
        String params = (result.getParameters() == null) ? "" : java.util.Arrays.toString(result.getParameters());
        return cls + "#" + method + params;
    }
}



