package com.xxgame.admin.web.modules.commons.repository;

import com.xxgame.admin.web.modules.commons.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 配置表
 * @author gil
 */
public interface PropertyRepository extends JpaRepository<Property, String> {

}
