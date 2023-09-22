package com.backend.TravelGuide.tourapi.controller;

import com.backend.TravelGuide.tourapi.DTO.TourAPIDTO;
import com.backend.TravelGuide.tourapi.service.TourAPIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Tag(name = "TourAPI 컨트롤러")
@RestController
@Slf4j
public class TourRestAPIController {
    @Qualifier("tourAPIServiceImpl")
    public final TourAPIService tourAPIService;

    @Value("${api.key}")
    String apiKey;

    public TourRestAPIController(TourAPIService tourAPIService) {
        this.tourAPIService = tourAPIService;
    }

    @Operation(summary = "키워드 검색", description = "키워드와 페이지 번호를 전달하면 여행지 목록을 JSON으로 반환합니다.")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK!", content = @Content(schema = @Schema(implementation = TourAPIDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @Parameters(
            {
            @Parameter(name = "keyword", description = "키워드", example = "타워"),
            @Parameter(name = "pageNo", description = "페이지 번호", example = "1")
    }
    )
    @GetMapping("/api/tourapi/keywordSearch")
    public List<?> tourListApiByKeyword(
            @RequestParam String keyword,
            @RequestParam String pageNo
    ) {

        List<TourAPIDTO> tourAPIDTOS = tourAPIService.keywordSearchApi(apiKey, keyword, pageNo);

        return tourAPIDTOS;
    }

    @Operation(summary = "지역 기반 검색", description = "선택한 지역의 여행지 목록을 가져옵니다.")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK!", content = @Content(schema = @Schema(implementation = TourAPIDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @Parameters(
            {
                    @Parameter(name = "areaCode", description = "지역번호 : 1, 2, 31", example = "타워"),
                    @Parameter(name = "pageNo", description = "페이지 번호", example = "1")
            }
    )
    @GetMapping("/api/tourapi/areaBasedSearch")
    public List<TourAPIDTO> tourListApiByArea(
            @RequestParam String areaCode,
            @RequestParam String pageNo
    ) {

        List<TourAPIDTO> tourAPIDTOS = tourAPIService.areaBasedSearchApi(apiKey, areaCode, pageNo);

        return tourAPIDTOS;
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, HttpServerErrorException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleMissingServletRequestParameterException() {
        log.info("예외 발생!\nMissingServletRequestParameterException");
        return "Bad Request";
    }

    @ExceptionHandler(JSONException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleJsonException() {
        log.info("예외 발생!\nJSONException");
        return "Server Error";
    }
}
