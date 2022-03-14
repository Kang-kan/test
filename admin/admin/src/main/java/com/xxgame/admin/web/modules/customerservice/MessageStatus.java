package com.xxgame.admin.web.modules.customerservice;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 留言状态
 */
public enum MessageStatus {

    /** 初始化 */
    NONE(0),

    /** 回复 */
    REPLY(1);

    private final int value;

    MessageStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * MessageStatus
     * @param value
     * @return
     */
    public static MessageStatus valueOf(int value) {
        Stream<MessageStatus> stream = Arrays.stream(MessageStatus.values());
        return stream.filter(status -> status.value == value).findFirst().get();
    }

}
