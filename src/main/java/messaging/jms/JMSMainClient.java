package messaging.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
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
 

 
public class JMSMainClient {
 
	public TransportConfiguration transportConfiguration;
	public ConnectionFactory factory;
	public Queue queue;
	public Connection connection;
	public Session session;
	

	public JMSMainClient(String queueName) {
	try{	
	  this.transportConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName());
	  this.factory = (ConnectionFactory) HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, transportConfiguration);
	  this.queue = HornetQJMSClient.createQueue(queueName);
	  this.connection = this.factory.createConnection();
	  this.session = this.connection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
	  }
	 catch (JMSException je){
		 je.printStackTrace();
	 }
	
	}
		
    public void send(String txtmsg) {

        try {
            MessageProducer producer = this.session.createProducer(this.queue);
            TextMessage message = this.session.createTextMessage();
            message.setText(txtmsg);
            producer.send(message);
 
            this.session.close();
            this.connection.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public String receive() {
    	String rmessage = "";
        try {
            MessageConsumer consumer = this.session.createConsumer(this.queue);
            TextMessage rmsg = (TextMessage) consumer.receive();
            rmessage = rmsg.getText();
            this.session.close();
            this.connection.close();
            return rmessage;
 
        } catch (Exception e) {
            return e.getMessage();
        }
        
        
    }
    
    
    
}

