package com.smikevon.easy.common.validator;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.smikevon.easy.common.validator.annotation.EnumContains;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by sean (smikevon@163.com) on 2018/9/7.
 */
@Slf4j
public class EnumContainsValidator implements ConstraintValidator<EnumContains, Integer> {

    private Set<Integer> candidates = new HashSet<>();

    @Override
    public void initialize(EnumContains constraintAnnotation) {
        try {
            Class<Enum> value = (Class<Enum>) constraintAnnotation.value();
            Field checkField = value.getDeclaredField(constraintAnnotation.checkField());
            if (!checkField.getType().isAssignableFrom(Integer.class)) {
                throw new RuntimeException("检查字段应整型");
            }

            checkField.setAccessible(true);
            for (Enum item : value.getEnumConstants()) {
                candidates.add((Integer) checkField.get(item));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return candidates.contains(value);
    }
}
