package com.umessage.letsgo.domain.vo.team.respone;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/12.
 */
public class WebMemberResponse extends CommonResponse {

    private Page<MemberEntity> memberList;
    private MemberEntity member;
    private long totals;
    private int pages;

    public Page<MemberEntity> getMemberList() {
        return memberList;
    }

    public void setMemberList(Page<MemberEntity> memberList) {
        this.memberList = memberList;
    }
    public void setMemberList(List<MemberEntity> memberList) {
        this.memberList = new Page<MemberEntity>();
        this.memberList.addAll(memberList);
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

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public static WebMemberResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends WebMemberResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        WebMemberResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static WebMemberResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends WebMemberResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        WebMemberResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

}
