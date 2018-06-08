
package com.example.alialzantot.retrofit.beans;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonImages {

    @SerializedName("profiles")
    @Expose
    private List<Profile> profiles = null;
    @SerializedName("id")
    @Expose
    private Integer id;

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
