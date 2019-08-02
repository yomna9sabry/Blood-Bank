
package com.example.bloodbank.data.model.favourite;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavouriteDataModel {

    @SerializedName("attached")
    @Expose
    private List<Object> attached = null;
    @SerializedName("detached")
    @Expose
    private List<Integer> detached = null;

    public List<Object> getAttached() {
        return attached;
    }

    public void setAttached(List<Object> attached) {
        this.attached = attached;
    }

    public List<Integer> getDetached() {
        return detached;
    }

    public void setDetached(List<Integer> detached) {
        this.detached = detached;
    }

}
