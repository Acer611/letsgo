package com.umessage.letsgo.domain.vo.notice.respone;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by pw on 2016/9/7.
 */
public class QuestionVo {
    @ApiModelProperty(value = "是否第一次访问 1是；0否")
    private Integer first;
    @ApiModelProperty(value = "调查问卷ID")
    private Long problem;

    @ApiModelProperty(value = "性别【1：男；2：女】")
    private Integer sex;

    @ApiModelProperty(value = "姓名")
    private String name;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Long getProblem() {
        return problem;
    }

    public void setProblem(Long problem) {
        this.problem = problem;
    }
}
