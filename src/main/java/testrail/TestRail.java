package testrail;

import java.io.IOException;
import ru.yandex.qatools.properties.annotations.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements test run results update to TestRail interface
 */
@Resource.Classpath("testrail.properties")
public class TestRail {
    private static final TestRailProperties TEST_RAIL_PROPERTIES = new TestRailProperties();
    private static final String ENDPOINT = TEST_RAIL_PROPERTIES.getEndPoint();
    private static final String USERNAME = TEST_RAIL_PROPERTIES.getUsername();
    private static final String PASSWORD = TEST_RAIL_PROPERTIES.getPassword();

    private final Map testResultData = new HashMap();
    private APIClient client = null;

    public TestRail() {
        client = new APIClient(ENDPOINT);
        client.setUser(USERNAME);
        client.setPassword(PASSWORD);
    }

    /**
     * Sets test execution result to test case in TestRail interface
     *
     * @param caseIds TestRail test case id's
     * @param run TestRail test suite run id
     * @param screenshotLink failed test screenshot link from Yandex Drive
     * @param failMessage failed test result message
     */
    public void setCaseResult(String[] caseIds, String run, boolean isResultFail, String screenshotLink,
            String failMessage) throws IOException, APIException {

        if (isResultFail) {
            testResultData.put("status_id", "5");
            testResultData.put("comment", "![Valid XHTML] (" + screenshotLink + ") \n" + failMessage + " ");
        } else {
            testResultData.put("status_id", "1");
        }

        for (int i = 0; i < caseIds.length; i++) {
            client.sendPost("add_result_for_case/" + run + "/" + caseIds[i], testResultData);
        }
    }
}