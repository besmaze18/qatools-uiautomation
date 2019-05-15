package testrail.yandexdrive;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Client interface for Yandex Drive API
 */
public interface YandexApiServiceClient {

    @GET("/v1/disk/resources/upload")
    Call<YandexDriveData> getUploadedUrl(@Header("Authorization") String authToken,
            @Query("path") String path);

    @GET("/v1/disk/resources/download")
    Call<YandexDriveData> getFileDownloadLink(@Header("Authorization") String authToken,
            @Query("path") String path);
}
