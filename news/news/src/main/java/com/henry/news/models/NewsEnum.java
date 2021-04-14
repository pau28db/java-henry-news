package com.henry.news.models;

public enum NewsEnum {
    VIDEO("Video News"),
    IMAGES("Image News"),
    TEXT("Text News");

    private String description;

    NewsEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static NewsEnum find(String value){
        for( NewsEnum v: values()){
            if(v.toString().equalsIgnoreCase(value)){
                return v;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid NewsType: %s", value));
    }
}
