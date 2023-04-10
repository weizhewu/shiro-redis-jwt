package com.waltz.springshirostudy.controller;

import com.waltz.springshirostudy.common.result.ResponseResult;
import com.waltz.springshirostudy.common.result.ResultCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 异常处理请求（用于处理拦截器中的一些异常错误的统一处理）
 * @createDate: 2023/4/7 11:23
 **/
@RestController
@CrossOrigin
@RequestMapping("/exception")
public class TokenErrorController {
    @RequestMapping("/authenticationException")
    public ResponseResult<ResultCode> authenticationException(){
        return ResponseResult.failure(ResultCode.USER_NOT_LOGGED_IN);
    }
}
