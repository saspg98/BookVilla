package book_model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ReadingModes implements Serializable {

    @Expose(deserialize = false)
    private boolean text;

    @Expose(deserialize = false)
    private boolean image;
}
