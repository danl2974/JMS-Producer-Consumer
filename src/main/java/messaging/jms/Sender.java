package messaging.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Sender {
 private ConnectionFactory cf;
 private Connection c;
 private Session s;
 private Destination d;
 private MessageProducer mp;

 public Sender() throws NamingException, JMSException {
  InitialContext init = new InitialContext();
  this.cf = (ConnectionFactory) init.lookup("/ConnectionFactory");
  this.d = (Destination) init.lookup("/queues/OrderQueue");
  this.c = (Connection) this.cf.createConnection("quickstartUser", "quickstartPwd1!");
  this.c.start();
  this.s = this.c.createSession(false, Session.AUTO_ACKNOWLEDGE);
  this.mp = this.s.createProducer(this.d);
 }

 public void send(String string) throws JMSException {
  TextMessage tm = this.s.createTextMessage(string);
  this.mp.send(tm);
 }

 public void close() throws JMSException {
  this.c.close();
 }




}