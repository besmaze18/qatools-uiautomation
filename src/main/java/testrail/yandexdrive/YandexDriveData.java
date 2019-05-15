package testrail.yandexdrive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class describes Yandex Drive API response body parameters
 */
public class YandexDriveData {

    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("templated")
    @Expose
    private Boolean templated;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Boolean getTemplated() {
        return templated;
    }

    public void setTemplated(Boolean templated) {
        this.templated = templated;
    }
}
