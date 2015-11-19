package ca.ulaval.glo4003.housematch.web.security;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import ca.ulaval.glo4003.housematch.web.security.SecurityBeanPostProcessor;

public class SecurityBeanPostProcessorTest {

    private static final String SAMPLE_BEAN_NAME = "bean";

    SecurityBeanPostProcessor securityBeanPostProcessor;

    PermissionEvaluator permissionEvaluatorMock;
    DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandlerMock;
    DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandlerMock;

    @Before
    public void init() {
        permissionEvaluatorMock = mock(PermissionEvaluator.class);
        defaultMethodSecurityExpressionHandlerMock = mock(DefaultMethodSecurityExpressionHandler.class);
        defaultWebSecurityExpressionHandlerMock = mock(DefaultWebSecurityExpressionHandler.class);
        securityBeanPostProcessor = new SecurityBeanPostProcessor(permissionEvaluatorMock);
    }

    @Test
    public void postProcessorSetsThePermissionEvaluatorOnTheDefaultMethodSecurityExpressionHandler() {
        securityBeanPostProcessor.postProcessAfterInitialization(defaultMethodSecurityExpressionHandlerMock, SAMPLE_BEAN_NAME);
        verify(defaultMethodSecurityExpressionHandlerMock).setPermissionEvaluator(permissionEvaluatorMock);
    }

    @Test
    public void postProcessorSetsThePermissionEvaluatorOnTheDefaultWebSecurityExpressionHandler() {
        securityBeanPostProcessor.postProcessAfterInitialization(defaultWebSecurityExpressionHandlerMock, SAMPLE_BEAN_NAME);
        verify(defaultWebSecurityExpressionHandlerMock).setPermissionEvaluator(permissionEvaluatorMock);
    }

}
