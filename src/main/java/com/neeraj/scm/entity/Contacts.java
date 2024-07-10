package com.neeraj.scm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Contacts {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    @Column(length = 1000)
    private String picture;

    @Column(length = 1000)
    private String description;

    private boolean favoriteContact ;

    private String websiteLink;
    private String linkedInLink;
    private String address;

    @OneToMany(mappedBy = "contacts", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> socialLinks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id_contacts")
    @JsonIgnore
    private User user;
}
