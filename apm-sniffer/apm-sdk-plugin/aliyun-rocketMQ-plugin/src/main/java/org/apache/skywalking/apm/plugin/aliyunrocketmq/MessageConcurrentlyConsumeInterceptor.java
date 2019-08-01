package org.apache.skywalking.apm.plugin.aliyunrocketmq;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.Message;
import org.apache.skywalking.apm.agent.core.context.CarrierItem;
import org.apache.skywalking.apm.agent.core.context.ContextCarrier;
import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.context.tag.Tags;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.agent.core.context.trace.SpanLayer;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import org.apache.skywalking.apm.network.trace.component.ComponentsDefine;

import java.lang.reflect.Method;

/**
 * @author : Kevin.
 * @version 0.1 : MessageConsumeInterceptor v0.1 2019-07-25 18:31 By Kevin.
 * @description :
 */
public class MessageConcurrentlyConsumeInterceptor implements InstanceMethodsAroundInterceptor {

    public static final String CONCURRENTLY_CONSUME_OPERATION_NAME_PREFIX = "AliyunRocketMQ/";

    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                             MethodInterceptResult result) throws Throwable {
        Message msgs = (Message)allArguments[0];
        ContextCarrier contextCarrier = getContextCarrierFromMessage(msgs);
        AbstractSpan span = ContextManager.createEntrySpan(CONCURRENTLY_CONSUME_OPERATION_NAME_PREFIX + msgs.getTopic() + "/Consumer", contextCarrier);
        span.setComponent(ComponentsDefine.ALIYUN_ROCKET_MQ_CONSUMER);
        SpanLayer.asMQ(span);
        ContextManager.extract(getContextCarrierFromMessage(msgs));
    }

    @Override public final void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments,
                                                      Class<?>[] argumentsTypes, Throwable t) {
        ContextManager.activeSpan().errorOccurred().log(t);
    }

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                              Object ret) throws Throwable {
        Action status = (Action)ret;
        if (status == Action.ReconsumeLater) {
            AbstractSpan activeSpan = ContextManager.activeSpan();
            activeSpan.errorOccurred();
            Tags.STATUS_CODE.set(activeSpan, status.name());
        }
        ContextManager.stopSpan();
        return ret;
    }

    private ContextCarrier getContextCarrierFromMessage(Message message) {
        ContextCarrier contextCarrier = new ContextCarrier();

        CarrierItem next = contextCarrier.items();
        while (next.hasNext()) {
            next = next.next();
            next.setHeadValue(message.getUserProperties(next.getHeadKey()));
        }

        return contextCarrier;
    }
}
