package app.library.management.config.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	private static final String PROPERTIES_FILE_PATH = "application.properties";
	private static final Properties properties;

	static {
		properties = new Properties();
		try {
			properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

}
