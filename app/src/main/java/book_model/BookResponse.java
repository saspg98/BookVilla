package book_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// This class is used to map the Json object(containing list of Books) received as response.

public class BookResponse {

    @Expose(deserialize = false)
    private String kind;

    @SerializedName("items")
    private List<Book> items;

    @SerializedName("totalItems")
    private int totalItems;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    //List to store the books received as response of the query.
    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}

