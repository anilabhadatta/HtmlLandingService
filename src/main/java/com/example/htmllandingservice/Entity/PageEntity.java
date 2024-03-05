package com.example.htmllandingservice.Entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
@Table(name = "PAGE_DETAILS")
public class PageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "HTML_PAGE")
    private String htmlPage;

}
