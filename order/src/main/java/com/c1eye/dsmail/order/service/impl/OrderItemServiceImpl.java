package com.c1eye.dsmail.order.service.impl;

import com.c1eye.dsmail.order.entity.OrderReturnReasonEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.common.utils.Query;

import com.c1eye.dsmail.order.dao.OrderItemDao;
import com.c1eye.dsmail.order.entity.OrderItemEntity;
import com.c1eye.dsmail.order.service.OrderItemService;


@Service("orderItemService")
@RabbitListener(queues = {"hello-java-queue"})
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
                                               );

        return new PageUtils(page);
    }


    /**
     * queues：声明需要监听的队列
     * channel：当前传输数据的通道
     */
    @RabbitHandler
    public void revieveMessage(Message message,
                               OrderReturnReasonEntity content, Channel channel) {
        //拿到主体内容
        byte[] body = message.getBody();
        //拿到的消息头属性信息
        MessageProperties messageProperties = message.getMessageProperties();
        System.out.println("接受到的消息...内容" + message + "===内容：" + content);
        long deliveryTag = messageProperties.getDeliveryTag();
        try {
            if(deliveryTag % 2 == 0){
                // 收到
                channel.basicAck(messageProperties.getDeliveryTag(),false);
            }else {
                // 拒收 批量 是否重新入队
                channel.basicNack(deliveryTag,false,true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}