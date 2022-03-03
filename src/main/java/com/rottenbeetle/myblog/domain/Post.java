package com.rottenbeetle.myblog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String anons;
    private String fullText;
    private int views;
    private String date;

    public Post(String title, String anons, String fullText, int views, String date) {
        this.title = title;
        this.anons = anons;
        this.fullText = fullText;
        this.views = views;
        this.date = date;
    }
}
