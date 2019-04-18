package book_model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class SaleInfo implements Serializable {

    @Expose(deserialize = false)
    private String country;

    @Expose(deserialize = false)
    private String buyLink;
}
