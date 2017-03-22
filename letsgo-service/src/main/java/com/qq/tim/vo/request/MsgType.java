package com.qq.tim.vo.request;

/**
 * Created by wendy on 2016/6/13.
 */
public class MsgType {
    private final static String TIMTextElem = "TIMTextElem";    // 文本消息

    private final static String TIMFaceElem = "TIMFaceElem";    // 表情消息

    private final static String TIMLocationElem = "TIMLocationElem";    // 位置消息

    private final static String TIMCustomElem = "TIMCustomElem";    // 自定义消息

    public static String getTIMTextElem() {
        return TIMTextElem;
    }

    public static String getTIMFaceElem() {
        return TIMFaceElem;
    }

    public static String getTIMLocationElem() {
        return TIMLocationElem;
    }

    public static String getTIMCustomElem() {
        return TIMCustomElem;
    }
}
