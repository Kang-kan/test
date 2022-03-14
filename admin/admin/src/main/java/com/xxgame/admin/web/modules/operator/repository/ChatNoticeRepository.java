package com.xxgame.admin.web.modules.operator.repository;

import com.xxgame.admin.web.modules.operator.entity.ChatNotice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 跑马灯公告
 * @author gil
 *
 */
public interface ChatNoticeRepository extends JpaRepository<ChatNotice, Long> {

}
