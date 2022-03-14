package com.xxgame.admin.web.modules.commons.service;

import com.xxgame.admin.web.modules.commons.entity.Property;
import com.xxgame.admin.web.modules.commons.repository.PropertyRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 配置
 * @author gil
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public String getProperty(String propKey) {
        Property property = propertyRepository.getOne(propKey);
        if (property == null) {
            return null;
        }

        return property.getPropValue();
    }

    @Override
    public int getInt(String propKey) {
        String value = this.getProperty(propKey);
        if (StringUtils.isBlank(value)) {
            return 0;
        }

        return Integer.parseInt(value);
    }

}
