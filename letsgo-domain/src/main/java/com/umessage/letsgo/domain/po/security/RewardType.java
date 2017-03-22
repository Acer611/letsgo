package com.umessage.letsgo.domain.po.security;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wendy on 2016/9/22.
 */
public class RewardType {

    private String descn;   // 奖励描述

    private Double amount;  // 奖励金额

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public static Double getAmount(String name) {
        switch (name) {
            case "JOIN":
                return 50.0;
            case "INVITE":
                return 50.0;
            case "SHARE":
                return 50.0;
            case "TOUR":
                return 50.0;
            default:
                return 0.0;
        }
    }

    public static String getDescn(String name) {
        switch (name) {
            case "JOIN":
                return "邀请奖励（未带团）";
            case "INVITE":
                return "邀请奖励";
            case "SHARE":
                return "分享奖励";
            case "TOUR":
                return "带团奖励";
            default:
                return "";
        }
    }
}
