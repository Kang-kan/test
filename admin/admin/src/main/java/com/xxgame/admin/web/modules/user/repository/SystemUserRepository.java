package com.xxgame.admin.web.modules.user.repository;

import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.modules.user.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 管理后台用户
 * @author gil
 *
 */
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    /**
     * 根据用户查找
     * @param userName
     * @return
     */
    SystemUser findByUserName(String userName);

    /**
     * 根据账号名模糊查找
     * @param userName
     * @return
     */
    @Query(value = "SELECT * FROM system_user t WHERE t.user_name LIKE %?1% LIMIT 10", nativeQuery = true)
    List<SystemUser> findByUserNameLike(String userName);

    /**
     * 查詢號碼
     * @param phone
     * @return
     */
    @Query(value = "SELECT * FROM system_user t WHERE t.ipone = :phone", nativeQuery = true)
    String getUserPhone(String phone);


}
