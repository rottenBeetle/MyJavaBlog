package com.rottenbeetle.myblog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String anons;
    private String fullText;
    private int views;
    private String date;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    private List<String> tags;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public String getUsername(){
        return user != null ? user.getUsername() : "Неизвестный";
    }

    public Post(String title, String anons, String fullText, int views, String date, List<String> tags, User user) {
        this.title = title;
        this.anons = anons;
        this.fullText = fullText;
        this.views = views;
        this.date = date;
        this.tags = tags;
        this.user = user;
    }
}
