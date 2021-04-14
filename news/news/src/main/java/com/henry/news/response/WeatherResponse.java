package com.henry.news.response;


import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class WeatherResponse {

@SerializedName("location")
    private Location location;
    @SerializedName("current")
    private Current current;

}
