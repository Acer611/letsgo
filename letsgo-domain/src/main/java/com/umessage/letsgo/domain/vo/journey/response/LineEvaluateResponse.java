package com.umessage.letsgo.domain.vo.journey.response;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

/**
 * Created by pw on 2016/8/31.
 */
public class LineEvaluateResponse extends CommonResponse {

    private LineListVo lineListVo;
    private Page<LineListVo> lineList;
    private long totals;
    private int pages;

    public static LineEvaluateResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends LineEvaluateResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        LineEvaluateResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public LineListVo getLineListVo() {
        return lineListVo;
    }

    public void setLineListVo(LineListVo lineListVo) {
        this.lineListVo = lineListVo;
    }

    public Page<LineListVo> getLineList() {
        return lineList;
    }

    public void setLineList(Page<LineListVo> lineList) {
        this.lineList = lineList;
    }

    public long getTotals() {
        return totals;
    }

    public void setTotals(long totals) {
        this.totals = totals;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
