package com.jazzcash.utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

public class Utility {
	private static final Logger logger = JazzCashLogger.getLogger(Utility.class.getName());
	public static Properties properties = null;

	private synchronized static void loadProperties() throws Exception {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(Constant.USER_DIR + Constant.APP_ROOT_DIR + File.separator + Constant.APP_DIR + File.separator + Constant.CONFIG_DIR + File.separator + Constant.CONFIG_FILE_NAME));
		} catch (Exception e) {
			logger.error("Prop: loading failed", e);
			throw e;
		} finally {
		}
	}

	public static String getProperty(String propertyName) {
		String propertyValue = null;
		if (Constant.isProd) {
			try {
				propertyValue = System.getenv(propertyName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (propertyValue == null) {
				logger.error("Prop: " + propertyName + " not found");
			}
		} else {
			try {
				if (properties == null) {
					Utility.loadProperties();
				}
				propertyValue = properties.getProperty(propertyName);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (propertyValue == null) {
					logger.error("Prop: " + propertyName + " not found");
				}
			}
		}
		return propertyValue;
	}

	public static Long getJavaMillis() {
		return System.currentTimeMillis();
	}
}
