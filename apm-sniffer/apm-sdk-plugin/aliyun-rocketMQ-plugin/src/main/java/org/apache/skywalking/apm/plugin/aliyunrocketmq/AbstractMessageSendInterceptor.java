package org.apache.skywalking.apm.plugin.aliyunrocketmq;

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
import org.apache.skywalking.apm.util.StringUtil;

import java.lang.reflect.Method;

/**
 * @author : Kevin.
 * @version 0.1 : AbstractMessageSendInterceptor v0.1 2019-08-01 16:38 By Kevin.
 * @description :
 */
public abstract class AbstractMessageSendInterceptor implements InstanceMethodsAroundInterceptor {

    private static final String MESSAGE_SEND_OPERATION_NAME_PREFIX = "AliyunRocketMQ/";

    private static final String DEFAULT_BROKER = "aliyun-rocketMQ";

    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                             MethodInterceptResult result) throws Throwable {
        Message message = (Message)allArguments[0];
        ContextCarrier contextCarrier = new ContextCarrier();
        String namingServiceAddress = String.valueOf(objInst.getSkyWalkingDynamicField());
        AbstractSpan span = ContextManager.createExitSpan(buildOperationName(message.getTopic()), contextCarrier, namingServiceAddress);
        span.setComponent(ComponentsDefine.ALIYUN_ROCKET_MQ_PRODUCER);
        Tags.MQ_BROKER.set(span, DEFAULT_BROKER);
        Tags.MQ_TOPIC.set(span, message.getTopic());
        SpanLayer.asMQ(span);

        CarrierItem next = contextCarrier.items();
        while (next.hasNext()) {
            next = next.next();
            if (!StringUtil.isEmpty(next.getHeadValue())) {
                message.putUserProperties(next.getHeadKey(), next.getHeadValue());
            }
        }
    }

    private String buildOperationName(String topicName) {
        return MESSAGE_SEND_OPERATION_NAME_PREFIX + topicName + "/Producer";
    }
}
