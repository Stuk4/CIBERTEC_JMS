package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class JMSServer implements MessageListener {
	private static final Logger LOG = Logger.getLogger(JMSServer.class);
	private static final String JMS_CONN_FAC = "jms/QueueCF";
	private static final String JMS_QUEUE_IN = "jms/QueueIN";
	private static final String JMS_QUEUE_OUT = "jms/QueueOUT";

	private Context ctx;
	private ConnectionFactory factory;
	private Connection connection;
	private Session session;
	private Destination colaIN;
	private Destination colaOUT;
	private MessageProducer producer;
	private MessageConsumer consumer;

	public void execute() {
		try {
			createConnectionAndSession();
			createDestinationAndStart();
			// Estamos seguros que se creo conexion, session y destinos
			//Creamos el selector
			String selector = "OPERACION = 'Recarga'";
			consumer = session.createConsumer(colaIN, selector);
			consumer.setMessageListener(this);
			LOG.info("Esperando por mensaje...");
		} catch (Exception ex) {
			LOG.error("Error al procesar mensaje", ex);
		}
	}

	@Override
	public void onMessage(Message message) {
		try {
			//Recepcion del mensaje
			MapMessage msgReq = (MapMessage)message;
			LOG.info("Recibido=[" + msgReq + "]");
			LOG.info("Operacion=[" + msgReq.getString("OPERACION") + "]");
			LOG.info("Monto=[" + msgReq.getDouble("MONTO") + "]");
			
			//Ahora le respondemos
			MessageProducer producer = session.createProducer(colaOUT);
			TextMessage msgResp = session.createTextMessage("OK");
			msgResp.setJMSCorrelationID(msgReq.getJMSCorrelationID());
			producer.send(msgResp);
			producer.close();
			LOG.info("Mensaje enviado=[" + msgResp + "]");
			
		} catch (Exception ex) {
			LOG.error("Error al procesar mensaje", ex);
		}
	}

	private void createConnectionAndSession() {
		try {
			ctx = new InitialContext();
			factory = (ConnectionFactory) ctx.lookup(JMS_CONN_FAC);
			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (Exception ex) {
			LOG.error("Error al obtener la session", ex);
		}
	}

	private void createDestinationAndStart() {
		try {
			colaIN = (Destination) ctx.lookup(JMS_QUEUE_IN);
			colaOUT = (Destination) ctx.lookup(JMS_QUEUE_OUT);
			connection.start();
		} catch (Exception ex) {
			LOG.error("Error al obtener las colas", ex);
		}
	}
	
	public static void main(String[] args) {
		JMSServer server = new JMSServer();
		server.execute();
	}
}
