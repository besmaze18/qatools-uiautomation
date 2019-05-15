package testrail.yandexdrive;

import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

/**
 * This class gets parameters from yandexdrive.properties file
 */
@Resource.Classpath("yandexdrive.properties")
public class YandexDriveApiProperties {

    @Property("baseUrl")
    private String baseUrl;

    @Property("accessToken")
    private String accessToken;

    public YandexDriveApiProperties() {

        PropertyLoader.populate(this);
    }

    /**
     * Get Yandex Drive base URL parameter value from yandexdrive.properties file
     *
     * @return String value of baseUrl parameter
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Get Yandex Drive access token parameter value from yandexdrive.properties file
     *
     * @return String value of access token parameter
     */
    public String getAccessToken() {
        return accessToken;
    }
}
