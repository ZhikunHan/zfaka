package org.hantiv.zfaka.validator;

import org.hantiv.zfaka.exception.ErrorCode;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author Zhikun Han
 * @Date Created in 10:10 2022/7/13
 * @Description:
 */
@Component
public class ObjectValidator implements ErrorCode {
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public Map<String, String> validate(Object obj) {
        if(obj == null){
            return null;
        }
        Map<String, String> result = new HashMap<>();
        Set<ConstraintViolation<Object>> set = validator.validate(obj);
        if(set != null && set.size() > 0) {
            for(ConstraintViolation cv : set) {
                result.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
        }
        return result;
    }
}
