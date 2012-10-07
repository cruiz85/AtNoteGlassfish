package lector.server;

import java.util.StringTokenizer;

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
	
	public static String removeSpaces(String s) {
		StringTokenizer st = new StringTokenizer(s, " ", false);
		String t = "";
		while (st.hasMoreElements()) {
			t += st.nextElement();
		}
		return t;
	}

}
