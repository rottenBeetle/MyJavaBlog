package com.rottenbeetle.myblog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private String category;
    private String refCourse;

    public Product(String title, String shortDescription, String fullDescription, String refImage, int price, String category, String refCourse) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.refImage = refImage;
        this.price = price;
        this.category = category;
        this.refCourse = refCourse;
    }
}
