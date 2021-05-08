package me.ahoo.eventbus.spring.boot.autoconfigure.rabbit;

import me.ahoo.eventbus.spring.boot.autoconfigure.EnabledSuffix;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ahoo wang
 * create time 2020/5/14 22:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ConditionalOnProperty(value = RabbitProperties.PREFIX + EnabledSuffix.SUFFIX, havingValue = "true", matchIfMissing = true)
public @interface ConditionalOnRabbitEnabled {

}
