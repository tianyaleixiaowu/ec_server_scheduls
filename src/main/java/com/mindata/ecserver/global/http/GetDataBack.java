package com.mindata.ecserver.global.http;

/**
 * @author wuweifeng wrote on 2017/12/15.
 */
public interface GetDataBack<T> {
    void success(T t);
}
