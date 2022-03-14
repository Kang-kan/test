package com.xxgame.admin.web.modules.customerservice.repository;

import com.xxgame.admin.web.modules.customerservice.entity.ChatBlockWord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 聊天屏蔽词库
 * @author gil
 *
 */
public interface ChatBlockWordRepository extends JpaRepository<ChatBlockWord, Integer> {

}
