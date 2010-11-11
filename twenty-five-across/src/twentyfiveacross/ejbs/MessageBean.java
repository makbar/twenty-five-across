package twentyfiveacross.ejbs;

import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@MessageDriven
public class MessageBean implements MessageListener {
    Logger logger = Logger.getLogger("test");

    TopicConnectionFactory factory;
    TopicConnection connection;
    TopicSession session;
    Topic topic;
    TopicSubscriber subscriber;

    public MessageBean(String factoryName, String topicName) throws Exception {
        InitialContext jndiContext = new InitialContext();  // TODO: is this right?

        factory = (TopicConnectionFactory) jndiContext.lookup(factoryName);

        connection = factory.createTopicConnection();

        session = connection.createTopicSession(true, 0); /* No explanation found about these args */

        topic = (Topic) jndiContext.lookup(topicName); /* topicName = puzzle ID? */

        subscriber = session.createSubscriber(topic);

        subscriber.setMessageListener(this);

        connection.start();
    }

    public void onMessage(Message msg) {
        try {
            String name = msg.getStringProperty("name");
            logger.info("Received msg " + msg + ", from " + name);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}