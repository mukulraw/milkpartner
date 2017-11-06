package com.youthlive.milkpartner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.youthlive.milkpartner.StatePOJO.StateList;

import java.util.List;

/**
 * Created by tvs on 11/6/2017.
 */

public class StateBean {


    @SerializedName("state_list")
    @Expose
    private List<StateList> stateList = null;

    public List<StateList> getStateList() {
        return stateList;
    }

    public void setStateList(List<StateList> stateList) {
        this.stateList = stateList;
    }

}
