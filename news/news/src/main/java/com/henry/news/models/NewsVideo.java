package com.henry.news.models;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class NewsVideo extends News{

    private String videoTitle;
    private String videoDescription;
    private String videoUrl;

    @Override
    public NewsEnum newsEnum() {
        return NewsEnum.VIDEO;
    }
}
