package com.rottenbeetle.myblog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String shortDescription;
    private String fullDescription;
    private String refImage;
    private int price;
    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;
    private String author;
    private String refCourse;

    public Product(String title, String shortDescription, String fullDescription, String refImage, int price, Category category, String author, String refCourse) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.refImage = refImage;
        this.price = price;
        this.category = category;
        this.author = author;
        this.refCourse = refCourse;
    }

}
