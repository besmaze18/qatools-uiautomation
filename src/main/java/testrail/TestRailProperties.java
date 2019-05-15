package testrail;

import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

/**
 * This class gets parameters from testrail.properties file
 */
@Resource.Classpath("testrail.properties")
public class TestRailProperties {

    @Property("endPoint")
    private String endPoint;

    @Property("username")
    private String username;

    @Property("password")
    private String password;

    @Property("testrailRun")
    private String testrailRun;

    public TestRailProperties() {

        PropertyLoader.populate(this);
    }

    /**
     * Get TestRail endpoint parameter value from testrail.properties file
     *
     * @return String value of endpoint parameter
     */
    public String getEndPoint() {
        return endPoint;
    }

    /**
     * Get TestRail username parameter value from testrail.properties file
     *
     * @return String value of username parameter
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get TestRail password parameter value from testrail.properties file
     *
     * @return String value of password parameter
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get TestRail test suite run id parameter value from testrail.properties file
     *
     * @return String value of test suite run id parameter
     */
    public String getTestrailRun() {
        return testrailRun;
    }
}
