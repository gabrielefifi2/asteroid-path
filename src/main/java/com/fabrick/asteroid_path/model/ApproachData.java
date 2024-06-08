package com.fabrick.asteroid_path.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ApproachData {

    @SerializedName("close_approach_date")
    private String closeApproachDate;
    @SerializedName("orbiting_body")
    private String orbitingBody;
}
