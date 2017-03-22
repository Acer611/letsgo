package com.umessage.letsgo.domain.vo.security.respone;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2016/5/19.
 */
public class CardResponse extends CommonResponse{
    /**
     * 证件类型
     */
    @ApiModelProperty(value="证件类型")
    private String cardType;
    /**
     * 证件号码
     */
    @ApiModelProperty(value="证件号码")
    private String cardNum;
    /**
     * 证件有效日期
     */
    @ApiModelProperty(value="证件有效日期")
    private String cardTime;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardTime() {
        return cardTime;
    }

    public void setCardTime(String cardTime) {
        this.cardTime = cardTime;
    }

    @Override
    public String toString() {
        return "CardResponse{" +
                "cardType='" + cardType + '\'' +
                ", cardNum='" + cardNum + '\'' +
                ", cardTime='" + cardTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardResponse that = (CardResponse) o;

        return cardType.equals(that.cardType) && cardNum.equals(that.cardNum) && cardTime.equals(that.cardTime);

    }

    @Override
    public int hashCode() {
        String result = cardNum + cardTime + cardType;
        return result.hashCode();
    }
}
