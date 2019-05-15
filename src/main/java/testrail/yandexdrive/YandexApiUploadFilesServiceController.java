package testrail.yandexdrive;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import ru.yandex.qatools.properties.annotations.Resource;

/**
 * This class implements {@link YandexApiServiceClient} calls
 */
@Resource.Classpath("yandexdrive.properties")
public class YandexApiUploadFilesServiceController {

    private static final YandexDriveApiProperties yandexDriveApiProperties = new YandexDriveApiProperties();
    private static final String ACCESS_TOKEN = yandexDriveApiProperties.getAccessToken();

    /**
     * Get download link for file uploaded to Yandex Drive
     *
     * @param client Yandex Drive API client
     * @param fileName screenshot file name
     * @param file file to Yandex Drive upload
     *
     * @return link to download file from Yandex Drive
     */
    public String getDownloadLink(YandexApiServiceClient client, String fileName, File file) throws IOException {

        uploadFileByUrl(client, fileName, file);
        Call<YandexDriveData> yandexDriveDataCall = client
                .getFileDownloadLink(ACCESS_TOKEN, "Scr/" + fileName + ".png");
        YandexDriveData yandexDriveData = yandexDriveDataCall.execute().body();
        return yandexDriveData.getHref();
    }

    private String getUrlForUpload(YandexApiServiceClient client, String fileName) throws IOException {
        Call<YandexDriveData> call = client.getUploadedUrl(ACCESS_TOKEN, "Scr/" + fileName + ".png");
        Response<YandexDriveData> accessToken = call.execute();
        return accessToken.body().getHref();
    }

    private void uploadFileByUrl(YandexApiServiceClient client, String fileName, File file) throws IOException {
        String url = getUrlForUpload(client, fileName);
        URL urlForUpload = new URL(url);

        //upload file by url provided by Yandex Drive Api
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlForUpload)
                .put(reqFile)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .build();

        okHttpClient.newCall(request).execute();
    }
}
