package lector.server;

import javax.persistence.EntityTransaction;

import org.apache.logging.log4j.Logger;

public class ServiceManagerUtils {
	public static void rollback(EntityTransaction entityTransaction,
			Logger logger) {
		try {
			entityTransaction.rollback();
		} catch (Exception e) {
			logger.warn("Error making a rollback:", e);
		}

	}

	public static void rollback(EntityTransaction entityTransaction) {
		entityTransaction.rollback();

	}

}
