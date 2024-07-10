package com.neeraj.scm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    private String title;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contacts contacts;
}
