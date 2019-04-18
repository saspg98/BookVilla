package book_model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class PanelizationSummary implements Serializable {

    @Expose(deserialize = false)
    private boolean containsEpubBubbles;

    @Expose(deserialize = false)
    private boolean containsImageBubbles;
}
