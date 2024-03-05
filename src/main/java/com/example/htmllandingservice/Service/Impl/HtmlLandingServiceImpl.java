package com.example.htmllandingservice.Service.Impl;

import com.example.htmllandingservice.Entity.PageEntity;
import com.example.htmllandingservice.Repository.HtmlLandingRepository;
import com.example.htmllandingservice.Service.HtmlLandingService;
import com.example.htmllandingservice.dto.Request.PostHtmlPageDTO;
import com.example.htmllandingservice.dto.Response.HtmlPageResponseDTO;
import com.example.htmllandingservice.dto.Response.ResponseDTO;
import com.example.htmllandingservice.dto.Response.ResponseMetaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service("landingService")
public class HtmlLandingServiceImpl implements HtmlLandingService{

    @Autowired
    private HtmlLandingRepository landingRepository;

    @Override
    public ResponseMetaDTO savePageRecord(PostHtmlPageDTO request) {
        try{
            log.info("Setting page entity with data in request");
            PageEntity pageEntity = new PageEntity();
            pageEntity.setMobileNumber(request.getMobileNumber());
            pageEntity.setHtmlPage(request.getHtmlPage());
            log.info("Saving record in page entity");
            landingRepository.save(pageEntity);
            return new ResponseMetaDTO();
        }catch (Exception exception){
            log.error("Exception while saving record in database: ", exception);
            return failureResponse(exception);
        }
    }

    private ResponseMetaDTO failureResponse(Exception exception) {
        ResponseMetaDTO responseMetaDTO = new ResponseMetaDTO();
        responseMetaDTO.setCode("1");
        responseMetaDTO.setState("FAILURE");
        responseMetaDTO.setMessage(exception.getMessage());
        return responseMetaDTO;
    }

    @Override
    public ResponseDTO<HtmlPageResponseDTO> getPageRecord(String mobileNumber) {
        HtmlPageResponseDTO htmlPageResponseDTO = new HtmlPageResponseDTO();
        log.info("Finding record in database for mobileNumber: {}", mobileNumber);
        PageEntity pageEntity = landingRepository.findFirstByMobileNumber(mobileNumber);
        if(Objects.nonNull(pageEntity)){
            htmlPageResponseDTO.setMobileNumber(pageEntity.getMobileNumber());
            htmlPageResponseDTO.setHtmlPage(pageEntity.getHtmlPage());
            return setSuccessResponse(htmlPageResponseDTO);
        }
        return setFailureResponse();
    }

    private ResponseDTO<HtmlPageResponseDTO> setFailureResponse() {
        ResponseDTO<HtmlPageResponseDTO> responseDTO = new ResponseDTO<>();
        ResponseMetaDTO responseMetaDTO = new ResponseMetaDTO();
        responseMetaDTO.setCode("1");
        responseMetaDTO.setState("FAILURE");
        responseMetaDTO.setMessage("Record not found");
        responseDTO.setResponseMetaDTO(responseMetaDTO);
        return responseDTO;
    }

    private ResponseDTO<HtmlPageResponseDTO> setSuccessResponse(HtmlPageResponseDTO htmlPageResponseDTO) {
        ResponseDTO<HtmlPageResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setData(htmlPageResponseDTO);
        responseDTO.setResponseMetaDTO(new ResponseMetaDTO());
        return responseDTO;
    }
}
