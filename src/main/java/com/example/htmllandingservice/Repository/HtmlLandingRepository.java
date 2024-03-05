package com.example.htmllandingservice.Repository;

import com.example.htmllandingservice.Entity.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HtmlLandingRepository extends JpaRepository<PageEntity, Long> {
    PageEntity findFirstByMobileNumber(String mobileNumber);
}
