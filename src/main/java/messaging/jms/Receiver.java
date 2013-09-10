package messaging.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Receiver {
 private ConnectionFactory cf;
 private Connection c;
 private Session s;
 private Destination d;
 private MessageConsumer mc;

 public Receiver() throws NamingException, JMSException {
  InitialContext init = new InitialContext();
  this.cf = (ConnectionFactory) init.lookup("java:/ConnectionFactory");
  this.d = (Destination) init.lookup("queue/test");
  this.c = (Connection) this.cf.createConnection("jmsuser", "jms123");
  this.c.start();
  this.s = this.c.createSession(false, Session.AUTO_ACKNOWLEDGE);
  mc = s.createConsumer(d);
 }

 public String receive() throws JMSException {
  TextMessage msg = (TextMessage) mc.receive();
  this.s.close();
  return msg.getText();
 }

 public void close() throws JMSException {
  this.c.close();
 }



}