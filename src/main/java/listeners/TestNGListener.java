package listeners;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.service.ExtentService;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestNGListener implements ITestListener {

    @Override
    public void onStart(ITestContext iTestContext) {
        ExtentService.getInstance().setSystemInfo("os", System.getProperty("os.name").toLowerCase());
        try {
            ExtentService.getInstance().setSystemInfo("host", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            ExtentService.getInstance().setSystemInfo("host", "unknown");
        }
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        // Do nothing on start
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        Object attempt = iTestResult.getAttribute("RETRY_ATTEMPT");
        if (attempt != null) {
            ExtentCucumberAdapter.addTestStepLog("Passed after retry (attempt " + attempt + ")");
        }
        TestRetryListener.clear(iTestResult);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Boolean willRetry = (Boolean) iTestResult.getAttribute("WILL_RETRY");
        Object attempt = iTestResult.getAttribute("RETRY_ATTEMPT");

        if (Boolean.TRUE.equals(willRetry)) {
            ExtentCucumberAdapter.addTestStepLog(
                    "Failed on attempt " + attempt + " → will retry"
            );
        } else {
            ExtentCucumberAdapter.addTestStepLog(
                    "Failed (final). Attempts exhausted."
            );
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult){
        // Do nothing on test skipped
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        // Do nothing on test failed but within success percentage
    }

    @Override
    public void onFinish(ITestContext testContext) {
        // Do nothing on test finish
    }


}
