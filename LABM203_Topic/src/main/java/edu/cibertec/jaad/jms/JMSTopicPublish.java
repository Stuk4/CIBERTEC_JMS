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

public class JMSTopicPublish {
	private static final Logger LOG = Logger.getLogger(JMSTopicPublish.class);
	private static final String JMS_TOPIC = "jms/JAADTopic";
	private static final String JMS_CF = "jms/TopicCF";
	
	public static void main(String[] args) {
		try{
			Context ctx = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory) ctx.lookup(JMS_CF);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination topic = (Destination)ctx.lookup(JMS_TOPIC);
			connection.start();
			
			//Enviando el mensaje
			MessageProducer producer = session.createProducer(topic);
			ObjectMessage msgReq = session.createObjectMessage();
			msgReq.setObject(new Oferta("Recien Llegado", "Televisor", 20.0));
			producer.send(msgReq);
			LOG.info("Mensaje enviado:" + msgReq);
			
			producer.close();
			connection.close();
			System.exit(0);//FIXME retirame!!
			
		}catch(Exception ex){
			LOG.error("Error al enviar/recibir el mensaje", ex);
		}
	}
}
