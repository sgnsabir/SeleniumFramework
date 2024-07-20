package sleniumframework.testcomponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

    private int count = 0;
    private static final int maxTry = 2;

    @Override
    public boolean retry(ITestResult result) {
        System.out.println("Retrying test " + result.getName() + " with status " 
                + getResultStatusName(result.getStatus()) + " for the " + (count + 1) + " time(s).");
        if (count < maxTry) {
            count++;
            return true;
        }
        return false;
    }

    public String getResultStatusName(int status) {
        String resultName = null;
        if (status == 1)
            resultName = "SUCCESS";
        if (status == 2)
            resultName = "FAILURE";
        if (status == 3)
            resultName = "SKIP";
        return resultName;
    }
}