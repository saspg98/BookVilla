package book_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/*This class is used to deserialize the Json object(if contains these variables) into POJO(a simple java object).
*Annotations above each variable are used for mapping Json elements to POJO.
*Since some of the elements are present in Json are object other than primitive type variables, there
*corresponding classes are made separately inside package 'book_model' along with this class.
*Similar to this class they also contains getters setters to access details of the book.
 */

public class Book implements Serializable {

    @Expose(deserialize = false)
    private String kind;

    @Expose(deserialize = false)
    private String etag;

    @SerializedName("id")
    private String id;

    @Expose(deserialize = false)
    private String selfLink ;

    @Expose(deserialize = false)
    private SearchInfo searchInfo = new SearchInfo();

    @Expose(deserialize = false)
    private SaleInfo saleInfo = new SaleInfo();

    @SerializedName("accessInfo")
    private AccessInfo accessInfo = new AccessInfo();

    @SerializedName("volumeInfo")
    private VolumeInfo volumeInfo = new VolumeInfo();


    // Getter and Setter for variables required for access details of the book.

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

