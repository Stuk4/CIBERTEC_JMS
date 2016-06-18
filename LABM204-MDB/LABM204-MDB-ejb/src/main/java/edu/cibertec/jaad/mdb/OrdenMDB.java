package edu.cibertec.jaad.mdb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(
		name="OrdenMDB",
		mappedName="jms/MDBQueue",
		activationConfig = {
				@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
				@ActivationConfigProperty(propertyName="messageSelector", propertyValue="OPERACION = 'Recarga'")
		}
)
public class OrdenMDB implements MessageListener {
	private static final Logger LOG = LoggerFactory.getLogger(OrdenMDB.class);
	@Resource(mappedName="jms/QueueMDBCF")
	private ConnectionFactory factory;
	private Connection connection;
	
	public void onMessage(Message msg) {
		ObjectMessage message = (ObjectMessage)msg;
		LOG.info("Se obtuvo el mensaje:" + msg);
		try{
			Orden orden = (Orden)message.getObject();
			LOG.info("---------- Orden -----------");
			LOG.info("ID Cliente:" + orden.getIdCliente());
			LOG.info("Descripcion:" + orden.getDescripcion());
			LOG.info("Fecha Registro:" + orden.getFechaRegistro());
			LOG.info("Total:" + orden.getTotal());
			LOG.info("Orden:" + orden);
		}catch(Exception ex){
			LOG.error("Error al recibir el mensaje", ex);
		}
	}
	
	@PostConstruct
	public void init(){
		try{
			connection = factory.createConnection();
			connection.start();
			LOG.info("Recurso iniciados!!");
		}catch(Exception ex){
			LOG.error("Error al iniciar el MDB", ex);
		}
	}
	
	@PreDestroy
	public void end(){
		try{
			connection.close();
			LOG.info("Recursos devueltos!");
		}catch(Exception ex){
			LOG.error("Erro al liberar los recursos", ex);
		}
	}

}
