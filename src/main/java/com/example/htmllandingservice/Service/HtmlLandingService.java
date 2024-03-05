package com.example.htmllandingservice.Service;

import com.example.htmllandingservice.dto.Request.PostHtmlPageDTO;
import com.example.htmllandingservice.dto.Response.HtmlPageResponseDTO;
import com.example.htmllandingservice.dto.Response.ResponseDTO;
import com.example.htmllandingservice.dto.Response.ResponseMetaDTO;

public interface HtmlLandingService {

    ResponseMetaDTO savePageRecord(PostHtmlPageDTO request);
    ResponseDTO<HtmlPageResponseDTO> getPageRecord(String mobileNumber);

}
