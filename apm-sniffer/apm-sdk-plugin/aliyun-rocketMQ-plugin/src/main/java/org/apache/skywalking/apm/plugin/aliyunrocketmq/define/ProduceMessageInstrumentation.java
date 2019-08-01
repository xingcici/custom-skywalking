package org.apache.skywalking.apm.plugin.aliyunrocketmq.define;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;
import org.apache.skywalking.apm.agent.core.plugin.match.NameMatch;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author : Kevin.
 * @version 0.1 : ProduceMessageInstrumentation v0.1 2019-07-26 17:20 By Kevin.
 * @description :
 */
public class ProduceMessageInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {

    private static final String ENHANCE_CLASS = "com.aliyun.openservices.ons.api.impl.rocketmq.ProducerImpl";
    private static final String SEND_MESSAGE_METHOD_NAME = "send";
    private static final String MESSAGE_TRANSACTION_SEND_INTERCEPTOR = "org.apache.skywalking.apm.plugin.aliyunrocketmq.MessageSendInterceptor";

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
                        return named(SEND_MESSAGE_METHOD_NAME);
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return MESSAGE_TRANSACTION_SEND_INTERCEPTOR;
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
        return NameMatch.byName(ENHANCE_CLASS);
    }
}
