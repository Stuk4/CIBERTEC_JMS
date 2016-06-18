package edu.cibertec.jaad.jms;

import java.util.UUID;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class JMSClient {
	private static final Logger LOG = Logger.getLogger(JMSClient.class);
	private static final long WAITING_MSG = 10000l;
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
		createConnectionAndSession();
		createDestinationAndStart();
		processMessage();
		closeResources();
	}

	private void processMessage() {
		try {
			//Envio de mensaje
			MapMessage msgReq = session.createMapMessage();
			msgReq.setString("OPERACION", "Recarga");
			msgReq.setDouble("MONTO", 35.0);
			String correlationId = UUID.randomUUID().toString();
			msgReq.setJMSCorrelationID(correlationId);
			producer = session.createProducer(colaIN);
			//Creamos una propiedad en Message
			//msgReq.setStringProperty("OPERACION", "Recarga");
			//Enviamos
			producer.send(msgReq);
			LOG.info("Mensaje enviado=[" + msgReq + "]");
			
			//Recepcion de la respuesta
			String selector = "JMSCorrelationID = '" + correlationId + "'";
			LOG.info("Selector creado:[" + selector + "]");
			consumer = session.createConsumer(colaOUT,selector);
			LOG.info("Esperando por respuesta..." + WAITING_MSG + " miloseg.");
			TextMessage msgResp = (TextMessage)consumer.receive(WAITING_MSG);
			LOG.info("Mensaje recibido:" + msgResp);
			String result = msgResp == null
					? "SIN_RESPUESTA"
				    : msgResp.getText();
			LOG.info("Resultado:" + result);
		} catch (Exception ex) {
			LOG.error("Error al enviar y recibir el mensaje", ex);
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

	private void closeResources() {
		try {
			producer.close();
			consumer.close();
			session.close();
			connection.close();

			System.exit(0); // FIXME ...retirame
		} catch (Exception ex) {
			LOG.error("Error al cerrar los rescursos", ex);
		}
	}

	public static void main(String[] args) {
		JMSClient client = new JMSClient();
		client.execute();
	}
}
