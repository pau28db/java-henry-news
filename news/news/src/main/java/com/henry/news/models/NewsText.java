package com.henry.news.models;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class NewsText extends News{

    private String text;

    @Override
    public NewsEnum newsEnum() {
        return NewsEnum.TEXT;
    }
}
