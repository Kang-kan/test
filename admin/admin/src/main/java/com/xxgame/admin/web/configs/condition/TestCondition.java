package com.xxgame.admin.web.configs.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 是否是测试环境
 * @author gil
 *
 */
public class TestCondition implements Condition {

	/**
	 * profile name
	 */
	private final String profileName = "test";
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return context.getEnvironment().acceptsProfiles(profileName);
	}

}
