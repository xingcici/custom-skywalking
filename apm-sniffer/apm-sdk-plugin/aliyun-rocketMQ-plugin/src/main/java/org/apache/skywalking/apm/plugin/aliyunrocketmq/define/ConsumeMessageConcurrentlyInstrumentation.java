package org.apache.skywalking.apm.plugin.aliyunrocketmq.define;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;
import org.apache.skywalking.apm.agent.core.plugin.match.HierarchyMatch;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author : Kevin.
 * @version 0.1 : TransactionConsumerImplInstrumentation v0.1 2019-07-25 18:26 By Kevin.
 * @description :
 */
public class ConsumeMessageConcurrentlyInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {

    private static final String ENHANCE_CLASS = "com.aliyun.openservices.ons.api.MessageListener";
    private static final String CONSUME_MESSAGE_METHOD_NAME = "consume";
    private static final String MESSAGE_CONCURRENTLY_CONSUME_INTERCEPTOR = "org.apache.skywalking.apm.plugin.aliyunrocketmq.MessageConcurrentlyConsumeInterceptor";

    @Override
    protected ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return null;
    }

    @Override
    protected InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[]{
            new InstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return named(CONSUME_MESSAGE_METHOD_NAME);
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return MESSAGE_CONCURRENTLY_CONSUME_INTERCEPTOR;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }

    @Override
    protected ClassMatch enhanceClass() {
        return HierarchyMatch.byHierarchyMatch(new String[] {ENHANCE_CLASS});
    }
}
