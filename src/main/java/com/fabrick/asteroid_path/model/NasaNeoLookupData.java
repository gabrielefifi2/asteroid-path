package com.fabrick.asteroid_path.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class NasaNeoLookupData {

    @SerializedName("close_approach_data")
    private List<ApproachData> approachDataList;
}
