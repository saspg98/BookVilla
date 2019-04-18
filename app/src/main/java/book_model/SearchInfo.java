package book_model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class SearchInfo implements Serializable {

    @Expose(deserialize = false)
    private String textSnippet;
}
