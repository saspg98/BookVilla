package book_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AccessInfo implements Serializable {

    @Expose(deserialize = false)
    private String country;

    @Expose(deserialize = false)
    private String accessViewStatus;

    @Expose(deserialize = false)
    private Pdf pdf;

    @Expose(deserialize = false)
    private Epub epub;

    @SerializedName("webReaderLink")
    private String webReaderLink;

    public String getWebReaderLink() {
        return webReaderLink;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }
}
