package book_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class VolumeInfo implements Serializable {

    @SerializedName("title")
    private String title;

    @SerializedName("authors")
    private ArrayList<String> authors = new ArrayList<String>();

    @SerializedName("publishedDate")
    private String publishedDate;

    @Expose(deserialize = false)
    private ReadingModes readingModes = new ReadingModes();

    @Expose(deserialize = false)
    private String maturityRating;

    @Expose(deserialize = false)
    private boolean allowAnonLogging;

    @Expose(deserialize = false)
    private String contentVersion;

    @Expose(deserialize = false)
    private PanelizationSummary panelizationSummary = new PanelizationSummary();

    @SerializedName("imageLinks")
    private ImageLinks imageLinks = new ImageLinks();

    @Expose(deserialize = false)
    private String previewLink;

    @Expose(deserialize = false)
    private String infoLink;

    @Expose(deserialize = false)
    private String canonicalVolumeLink;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }
}
