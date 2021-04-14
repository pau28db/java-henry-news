package com.henry.news.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.io.Serializable;


@Data
@NoArgsConstructor
@Entity

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, property = "newsEnum")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewsImages.class, name = "IMAGES"),
        @JsonSubTypes.Type(value = NewsVideo.class, name = "VIDEO"),
        @JsonSubTypes.Type(value = NewsText.class, name = "TEXT"),
})
public abstract class News implements Serializable {

    @Id
    private Integer id;
    private String title;
    private String description;

    @AccessType(AccessType.Type.PROPERTY)
    public abstract NewsEnum newsEnum();

    @ManyToOne(fetch = FetchType.EAGER) //trae toda la lista de noticias, contrario LAZY
    @JoinColumn(name = "writer_id")
    private Writer owner;
}
