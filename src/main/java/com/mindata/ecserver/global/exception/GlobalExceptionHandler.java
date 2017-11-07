package com.mindata.ecserver.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author wuweifeng wrote on 2017/10/23.
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @ExceptionHandler(value = EcException.class)
    @ResponseBody
    public void ecFail(EcException e) {
        log(e);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public void fail(EcException e) {
        log(e);
    }

    private void log(Exception ex) {
        logger.error("************************异常开始*******************************");
        logger.error(ex.toString());

        StackTraceElement[] error = ex.getStackTrace();
        for (StackTraceElement stackTraceElement : error) {
            logger.error(stackTraceElement.toString());
        }
        logger.error("************************异常结束*******************************");
    }
}
