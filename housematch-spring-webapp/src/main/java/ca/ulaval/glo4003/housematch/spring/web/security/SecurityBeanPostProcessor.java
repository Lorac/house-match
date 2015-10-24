package ca.ulaval.glo4003.housematch.spring.web.security;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

public class SecurityBeanPostProcessor implements BeanPostProcessor, PriorityOrdered {

    PermissionEvaluator permissionEvaluator;

    public SecurityBeanPostProcessor(final PermissionEvaluator permissionEvaluator) {
        this.permissionEvaluator = permissionEvaluator;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof DefaultMethodSecurityExpressionHandler) {
            initDefaultMethodSecurityExpressionHandler((DefaultMethodSecurityExpressionHandler) bean);
        }
        if (bean instanceof DefaultWebSecurityExpressionHandler) {
            initDefaultWebSecurityExpressionHandler((DefaultWebSecurityExpressionHandler) bean);
        }
        if (bean instanceof SecurityContextHolderAwareRequestFilter) {
            initSecurityContextHolderAwareRequestFilter((SecurityContextHolderAwareRequestFilter) bean);
        }
        return bean;
    }

    private void initDefaultMethodSecurityExpressionHandler(DefaultMethodSecurityExpressionHandler handler) {
        handler.setDefaultRolePrefix(null);
        handler.setPermissionEvaluator(permissionEvaluator);
    }

    private void initDefaultWebSecurityExpressionHandler(DefaultWebSecurityExpressionHandler handler) {
        handler.setDefaultRolePrefix(null);
        handler.setPermissionEvaluator(permissionEvaluator);
    }

    private void initSecurityContextHolderAwareRequestFilter(SecurityContextHolderAwareRequestFilter filter) {
        filter.setRolePrefix("");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }
}
