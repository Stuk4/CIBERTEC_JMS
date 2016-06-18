package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class JMSReceiver {
	private static final Logger LOG = Logger.getLogger(JMSReceiver.class);
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
			
			MessageConsumer consumer = session.createConsumer(destination);
			LOG.info("Esperando por mensaje...");
			//TextMessage message = (TextMessage)consumer.receive();
			ObjectMessage message = (ObjectMessage)consumer.receive(10000);
			LOG.info("Mensaje recibido=[" + message + "]");
			Profesor profesor = (Profesor)message.getObject();
			LOG.info("Profesor recibido=[" + profesor + "]");
			
			consumer.close();
			session.close();
			connection.close();
			
			System.exit(0);//FIXME: Retirame
		}catch(Exception ex){
			LOG.error("Error al enviar el mensaje", ex);
		}
	}
}
