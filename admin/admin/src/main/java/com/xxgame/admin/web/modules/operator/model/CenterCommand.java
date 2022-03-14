package com.xxgame.admin.web.modules.operator.model;

/**
 * 中心服命令
 */
public interface CenterCommand {

    /**
     * 修改节点信息
     */
    int UPDATE_NODE = 3;

    /**
     * 修改世界服配置
     */
    int UPDATE_WORLD_SRV = 4;

    /**
     * 删除节点
     */
    int DEL_NODE = 5;

    /**
     * 删除世界服
     */
    int DEL_WORLD_SRV = 6;

    /**
     * 世界服信息
     */
    int WORLD_SRV_INFO = 7;

    /**
     * 列出游戏服配置
     */
    int LIST_GAME_SRV_INFOS = 8;

    /**
     * 列出节点信息
     */
    int LIST_NODE_INFOS = 9;

    /**
     * 查询节点信息
     */
    int NODE_INFO = 10;
}
