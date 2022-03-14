package com.xxgame.admin.web.configs.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 是否是生产环境
 * @author gil
 *
 */
public class ProductCondition implements Condition {

	/**
	 * profile name
	 */
	private final String profileName = "product";
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return context.getEnvironment().acceptsProfiles(profileName);
	}

}
