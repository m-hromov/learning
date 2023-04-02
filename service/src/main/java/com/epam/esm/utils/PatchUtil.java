package com.epam.esm.utils;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Component
public class PatchUtil extends BeanUtilsBean {
    @Override
    public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
        if (Objects.nonNull(value)) {
            super.copyProperty(bean, name, value);
        }
    }
}
