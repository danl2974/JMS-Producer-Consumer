package messaging.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
//import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.MessageConsumer;
//import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
 

 
public class JMSApplicationClient {
 
	public JMSApplicationClient() {
	}
		
    public void send(String txtmsg) {
        TransportConfiguration transportConfiguration =
                        new TransportConfiguration(
                NettyConnectorFactory.class.getName());  
 
        ConnectionFactory factory = (ConnectionFactory)
            HornetQJMSClient.createConnectionFactoryWithoutHA(
                JMSFactoryType.CF,
                transportConfiguration);
 
        //The queue name should match the jms-queue name in standalone.xml
        Queue queue = HornetQJMSClient.createQueue("testQueue");
        Connection connection;
        try {
            connection = factory.createConnection();
            Session session = connection.createSession(
                        false,
                        QueueSession.AUTO_ACKNOWLEDGE);
 
            MessageProducer producer = session.createProducer(queue);
 
            //1. Sending TextMessage to the Queue
            TextMessage message = session.createTextMessage();
            message.setText(txtmsg);
            producer.send(message);
 
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public String receive() {
        TransportConfiguration transportConfiguration =
                        new TransportConfiguration(
                NettyConnectorFactory.class.getName());  
 
        ConnectionFactory factory = (ConnectionFactory)
            HornetQJMSClient.createConnectionFactoryWithoutHA(
                JMSFactoryType.CF,
                transportConfiguration);

        Queue queue = HornetQJMSClient.createQueue("testQueue");
        Connection connection;
        try {
            connection = factory.createConnection();
            Session session = connection.createSession(
                        false,
                        QueueSession.AUTO_ACKNOWLEDGE);
 
            MessageConsumer consumer = session.createConsumer(queue);
 
            TextMessage rmsg = (TextMessage) consumer.receive();
            
            return rmsg.getText();
 
            
        } catch (Exception e) {
            return e.getMessage();
        }
        
        
    }
    
    
    
}
