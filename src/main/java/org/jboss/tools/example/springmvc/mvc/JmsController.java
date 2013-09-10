package org.jboss.tools.example.springmvc.mvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import messaging.jms.JMSClient;
//import messaging.jms.JMSApplicationClient;
import messaging.jms.JMSMainClient;
import messaging.jms.Receiver;
//import javax.jms.JMSException;
//import javax.naming.NamingException;



@Controller
@RequestMapping(value="/jms")
public class JmsController
{


    @RequestMapping(value="/{msgAction}", method=RequestMethod.GET)
    public @ResponseBody String responder(@PathVariable("msgAction") Long msgAction)
    {
    	if (msgAction == 1){
    		try{
    		   JMSMainClient jmsmc = new JMSMainClient("testQueue");
    		   Date timeNow = new Date();
    	       SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
    	       String txtmsg = "Message sent at: " + ft.format(timeNow);
               jmsmc.send(txtmsg);
               return txtmsg;
    		}
    		catch(Exception e){
    			return e.getMessage();
    		}
    		 
    	}
    	
    	
    	else if (msgAction == 2){
            
    		try{
    			Receiver rec = new Receiver();
    			String rmsg = rec.receive();
    			rec.close();
    			return rmsg;
    		 /*
    		  JMSMainClient jmsmc = new JMSMainClient("testQueue");
    		  String msg = jmsmc.receive();
    		  return msg;
    		  */
    		}
    		catch (Exception e) {
    			return e.getMessage();
    	         } 
    			
    	}
    	
    	
    	else {
    			return "No action specified";
    	}
    	
    	
    }

}