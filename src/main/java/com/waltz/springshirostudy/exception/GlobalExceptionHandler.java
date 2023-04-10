package com.waltz.springshirostudy.exception;

import com.alibaba.fastjson.JSON;
import com.waltz.springshirostudy.common.result.ResponseResult;
import com.waltz.springshirostudy.common.result.ResultCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author MrWei
 * @version 1.0.0
 * @date 2023/1/15 15:48
 * @description 全局异常处理
 */
@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 非法参数验证异常
     * @param e e
     * @return ResponseResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseResult<ResultCode> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<String> list = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors){
            list.add(fieldError.getDefaultMessage());
        }
        Collections.sort(list);
        log.error("methodArgumentNotValidExceptionHandler:[e:{}]", JSON.toJSONString(list));
        return ResponseResult.failure(ResultCode.METHOD_ARGUMENT_NOT_VALID);
    }

    /**
     * http解析请求参数异常
     * @param e e
     * @return ResponseResult
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseResult<ResultCode> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e){
        log.error("httpMessageNotReadableExceptionHandler:[e:{}]", e.getMessage());
        return ResponseResult.failure(ResultCode.MESSAGE_NOT_READABLE);
    }

    /**
     * 媒体类型异常
     * @param e e
     * @return ResponseResult
     */
    @ExceptionHandler(HttpMediaTypeException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<ResultCode> httpMediaTypeExceptionHandler(HttpMediaTypeException e){
        log.error("httpMediaTypeExceptionHandler:[e:{}]",e.getMessage());
        return ResponseResult.failure(ResultCode.HTTP_MEDIA_TYPE);
    }

    /**
     * 登录授权异常处理
     * @param e e
     * @return  ResponseResult
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<ResultCode> authenticationExceptionHandler(AuthenticationException e){
        log.error("authenticationExceptionHandler:[e:{}]",e.getLocalizedMessage());
        return ResponseResult.failure(ResultCode.AUTHENTICATION_EXCEPTION);
    }

    /**
     * 未授权异常处理
     * @param e e
     * @return ResponseResult
     */
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<ResultCode> unauthenticatedExceptionHandler(UnauthenticatedException e){
        log.error("unauthenticatedExceptionHandler:[e:{}]",e.getMessage());
        return ResponseResult.failure(ResultCode.UN_AUTHENTICATED_EXCEPTION);
    }

    /**
     * SQL语法异常处理
     * @param e e
     * @return ResponseResult
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<ResultCode> badSqlGrammarExceptionHandler(BadSqlGrammarException e){
        log.error("badSqlGrammarExceptionHandler:[message:{},sql:{},cause:{}]",e.getMessage(),e.getSql(),e.getCause());
        return ResponseResult.failure(ResultCode.BAD_SQL_GRAMMAR);
    }

    /**
     * 数据库字段缺失
     * @param e e
     * @return ResponseResult
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<ResultCode> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e){
        log.error("dataIntegrityViolationExceptionHandler:[e:{}]",e.getMessage());
        return ResponseResult.failure(ResultCode.SQL_INTEGRITY_CONSTRAINT_VIOLATION);
    }

    /**
     * JWT验签失败
     * @param e SignatureException
     * @return ResponseResult
     */
    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<ResultCode> signatureExceptionHandler(SignatureException e){
        log.error("signatureExceptionHandler:[e:{}]",e.getMessage());
        return ResponseResult.failure(ResultCode.SYSTEM_TOKEN_ERROR);
    }

    /**
     * JWT构造异常
     * @param e MalformedJwtException
     * @return ResponseResult
     */
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseResult<ResultCode> malformedJwtExceptionHandler(MalformedJwtException e){
        log.error("malformedJwtExceptionHandler:[e:{}]",e.getMessage());
        return ResponseResult.failure(ResultCode.SYSTEM_TOKEN_ERROR);
    }
    /**
     * JWT过期异常
     * @param e ExpiredJwtException
     * @return ResponseResult
     */
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<ResultCode> expiredJwtExceptionHandler(SignatureException e){
        log.error("expiredJwtExceptionHandler:[e:{}]",e.getMessage());
        return ResponseResult.failure(ResultCode.SYSTEM_TOKEN_ERROR);
    }
    /**
     * JWT预期格式不正确
     * @param e UnsupportedJwtException
     * @return ResponseResult
     */
    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<ResultCode> unsupportedJwtExceptionHandler(UnsupportedJwtException e){
        log.error("unsupportedJwtExceptionHandler:[e:{}]",e.getMessage());
        return ResponseResult.failure(ResultCode.SYSTEM_TOKEN_ERROR);
    }
    /**
     * 方法参数异常
     * @param e IllegalArgumentException
     * @return ResponseResult
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<ResultCode> illegalArgumentExceptionHandler(IllegalArgumentException e){
        log.error("illegalArgumentExceptionHandler:[e:{}]",e.getMessage());
        return ResponseResult.failure(ResultCode.PARAM_IS_INVALID);
    }



    /**
     * 默认异常处理
     * @param e e
     * @return ResponseResult
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<ResultCode> exceptionHandler(Exception e) {
        log.error("exceptionHandler:[exception:{}],cause:{}", e.getMessage(),e.getCause());
        e.printStackTrace();
        return ResponseResult.failure(ResultCode.SYSTEM_EXCEPTION);
    }

}
