package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;


public class JMSSender {
	private static final Logger LOG = Logger.getLogger(JMSSender.class);
	private static final String CF_NAME = "jms/QueueCF";
	private static final String QUEUE_NAME = "jms/JAADQueue";
	public static void main(String[] args) {
		try{
			Context ctx = new InitialContext();
			ConnectionFactory cnFactory = (ConnectionFactory)ctx.lookup(CF_NAME);
			Destination destination = (Destination)ctx.lookup(QUEUE_NAME);
			
			Connection connection = cnFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			
			MessageProducer producer = session.createProducer(destination);
			//TextMessage message = session.createTextMessage("Hola Mundo JMS");
			ObjectMessage message = session.createObjectMessage();
			message.setObject(new Profesor("Manuel", "AAAAA"));
			producer.send(message);
			LOG.info("Mensaje enviado:[" + message + "]");
			
			producer.close();
			session.close();
			connection.close();
			
			System.exit(0);//FIXME: Retirame
		}catch(Exception ex){
			LOG.error("Error al enviar el mensaje", ex);
		}
	}
}
