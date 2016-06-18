package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ConnectionMetaData;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;


public class Metadata {
	private static final Logger LOG = 
			Logger.getLogger(Metadata.class);
	public static void main(String[] args) {
		try{
			Context ctx = new InitialContext();
			ConnectionFactory cnFactory = 
					(ConnectionFactory)ctx.lookup("jms/QueueCF");
			Connection connection = cnFactory.createConnection();
			ConnectionMetaData metadata = connection.getMetaData();
			
			LOG.info("JMS Version:" + metadata.getJMSMajorVersion() +
					"." + metadata.getJMSMinorVersion());
			LOG.info("JMS Provider:" + metadata.getJMSProviderName());
			LOG.info("JMS Provider Version:" +
					metadata.getProviderMajorVersion() +
					"." +
					metadata.getProviderMinorVersion());
			connection.close();
			//FIXME: Retirame por favor
			System.exit(0);
		}catch(Exception ex){
			LOG.error("Error al obtener la metadata", ex);
		}
	}
}
