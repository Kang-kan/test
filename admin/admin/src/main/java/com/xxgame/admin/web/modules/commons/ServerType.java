package com.xxgame.admin.web.modules.commons;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 服务器类型
 */
public enum ServerType {

    /** 正式服 */
    OFFICIAL(0),

    /** 测试服 */
    TEST(1),

    /** 审核服 */
    AUDIT(2);

    private final int value;

    ServerType(int value) {
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
    public static ServerType valueOf(int value) {
        Stream<ServerType> stream = Arrays.stream(ServerType.values());
        return stream.filter(status -> status.value == value).findFirst().get();
    }

}
