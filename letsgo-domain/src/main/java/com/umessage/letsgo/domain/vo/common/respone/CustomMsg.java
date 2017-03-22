package com.umessage.letsgo.domain.vo.common.respone;

import java.io.Serializable;

/**
 * Created by wendy on 2016/7/16.
 */
public class CustomMsg implements Serializable {
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CustomMsg{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
