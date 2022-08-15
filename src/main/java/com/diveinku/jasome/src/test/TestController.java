package com.diveinku.jasome.src.test;

import com.diveinku.jasome.src.response.BaseResponse;
import com.diveinku.jasome.src.response.BaseResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.diveinku.jasome.src.response.BaseResponseStatus.SUCCESS;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("")
    public BaseResponse<BaseResponseStatus> getTest(){
        return new BaseResponse<>(SUCCESS);
    }
}
