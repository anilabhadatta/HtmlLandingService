package com.example.htmllandingservice.Controller;

import com.example.htmllandingservice.Service.HtmlLandingService;
import com.example.htmllandingservice.dto.Request.PostHtmlPageDTO;
import com.example.htmllandingservice.dto.Response.HtmlPageResponseDTO;
import com.example.htmllandingservice.dto.Response.ResponseDTO;
import com.example.htmllandingservice.dto.Response.ResponseMetaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
public class HtmlController {

    @Autowired
    @Qualifier("landingService")
    private HtmlLandingService  landingService;

    @PostMapping("/page/save")
    public ResponseMetaDTO postHtmlPage(@RequestBody PostHtmlPageDTO request) {
        return landingService.savePageRecord(request);
    }

    @GetMapping("/page/get")
    public ResponseDTO<HtmlPageResponseDTO> getHtmlPage(@RequestParam String mobileNumber) {
        return landingService.getPageRecord(mobileNumber);
    }

}
