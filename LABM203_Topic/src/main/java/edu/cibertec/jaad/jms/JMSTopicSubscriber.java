package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;



public class JMSTopicSubscriber implements MessageListener{
	private static final Logger LOG = Logger.getLogger(JMSTopicSubscriber.class);
	private static final String JMS_TOPIC = "jms/JAADTopic";
	private static final String JMS_CF = "jms/TopicCF";
	
	private String id;
	private Session session;
	
	public JMSTopicSubscriber(String id){
		this.id = id;
	}
	
	public void start(){
		try{
			Context ctx = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory) ctx.lookup(JMS_CF);
			Connection connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination topic = (Destination)ctx.lookup(JMS_TOPIC);
			connection.start();
			
			MessageConsumer consumer = session.createConsumer(topic);
			consumer.setMessageListener(this);
			LOG.info("[" + id + "] Esperando por mensaje");
			
		}catch(Exception ex){
			LOG.error("Error al recibir el mensaje", ex);
		}
	}

	@Override
	public void onMessage(Message message) {
		try{
			ObjectMessage msgRecibido = (ObjectMessage)message;
			Oferta oferta = (Oferta)msgRecibido.getObject();
			LOG.info("[" + id + "] Recibido:" + msgRecibido);
			LOG.info("[" + id + "] Oferta:" + oferta);
		}catch(Exception ex){
			LOG.error("Error al procesar el mensaje", ex);
		}
	}
	
	public static void main(String[] args) {
		JMSTopicSubscriber subs = new JMSTopicSubscriber("A");
		subs.start();
	}
}
