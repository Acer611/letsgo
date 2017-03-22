package com.jrmf360.vo.response;
import java.util.Date;
/**
 * Created by fuzhili on 2016/9/28.
 */
public class StandardWalletResponse extends WalletCommonResponse{
    /**
     * 金融魔方订单号
     */
    private String jrmfOrderno;
    /**
     * 提现金额
     */
    private Double outMoney;
    /**
     * 提现日期
     */
    private Date outDate;
    /**
     * 预计到账日期
     */
    private Date arriveDate;
    /**
     * 到账银行
     */
    private String arriveBankName;
    /**
     * 到账卡号
     */
    private String arrivecardNo;

    public String getJrmfOrderno() {
        return jrmfOrderno;
    }

    public void setJrmfOrderno(String jrmfOrderno) {
        this.jrmfOrderno = jrmfOrderno;
    }

    public Double getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(Double outMoney) {
        this.outMoney = outMoney;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getArriveBankName() {
        return arriveBankName;
    }

    public void setArriveBankName(String arriveBankName) {
        this.arriveBankName = arriveBankName;
    }

    public String getArrivecardNo() {
        return arrivecardNo;
    }

    public void setArrivecardNo(String arrivecardNo) {
        this.arrivecardNo = arrivecardNo;
    }

    @Override
    public String toString() {
        return "StandardWalletResponse{" +
                "jrmfOrderno='" + jrmfOrderno + '\'' +
                ", outMoney=" + outMoney +
                ", outDate=" + outDate +
                ", arriveDate=" + arriveDate +
                ", arriveBankName='" + arriveBankName + '\'' +
                ", arrivecardNo='" + arrivecardNo + '\'' +
                '}';
    }
}
