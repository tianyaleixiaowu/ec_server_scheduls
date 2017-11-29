package com.mindata.ecserver.global.geo.retrofit.model.response;


import com.mindata.ecserver.global.geo.retrofit.model.BaseResult;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/24
 */

public class CoordinateResult extends BaseResult {

    private CoordinateResultBean result;

    private List<CoordinateResultBean> results;

    private List<GaodeCoordinateResultBean> geocodes;

    public CoordinateResultBean getResult() {
        return result;
    }

    public void setResult(CoordinateResultBean result) {
        this.result = result;
    }

    public List<CoordinateResultBean> getResults() {
        return results;
    }

    public void setResults(List<CoordinateResultBean> results) {
        this.results = results;
    }

    public List<GaodeCoordinateResultBean> getGeocodes() {
        return geocodes;
    }

    public void setGeocodes(List<GaodeCoordinateResultBean> geocodes) {
        this.geocodes = geocodes;
    }
}
