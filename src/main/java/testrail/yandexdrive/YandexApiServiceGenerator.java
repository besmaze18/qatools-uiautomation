package testrail.yandexdrive;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.yandex.qatools.properties.annotations.Resource;

/**
 * This class creates Yandex Drive API instance using {@link Retrofit}
 */
@Resource.Classpath("yandexdrive.properties")
public class YandexApiServiceGenerator {

    private static final YandexDriveApiProperties YANDEX_DRIVE_API_PROPERTIES = new YandexDriveApiProperties();
    private static final String API_BASE_URL = YANDEX_DRIVE_API_PROPERTIES.getBaseUrl();

    private YandexApiServiceGenerator() {
    }

    /**
     * Create an implementation of the API endpoints defined by the {@link YandexApiServiceClient} interface.
     */
    //TODO add authentication via username&password
    public static YandexApiServiceClient createService() {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return client.create(YandexApiServiceClient.class);
    }
}
