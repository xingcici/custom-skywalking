package org.apache.skywalking.apm.plugin.aliyunrocketmq;

import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;

import java.lang.reflect.Method;

/**
 * @author : Kevin.
 * @version 0.1 : MessageSendInterceptor v0.1 2019-07-25 15:59 By Kevin.
 * @description :
 */
public class MessageTransactionSendInterceptor extends AbstractMessageSendInterceptor {

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                              Object ret) throws Throwable {
        ContextManager.stopSpan();
        return ret;
    }

    @Override public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments,
                                                Class<?>[] argumentsTypes, Throwable t) {
        ContextManager.activeSpan().errorOccurred().log(t);
    }
}
