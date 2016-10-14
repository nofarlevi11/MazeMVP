package properties;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertiesLoader.
 */
public class PropertiesLoader {
	
	/** The instance. */
	private static PropertiesLoader instance;
	
	/** The properties. */
	private static Properties properties;

	/**
	 * Instantiates a new properties loader.
	 */
	private PropertiesLoader() {
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream("properties.xml"));
			Object result = decoder.readObject();
			properties = (Properties) result;
			decoder.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public static Properties getProperties() {
		return properties;
	}

	/**
	 * Gets the single instance of PropertiesLoader.
	 *
	 * @return single instance of PropertiesLoader
	 */
	public static PropertiesLoader getInstance() {
		if (instance == null)
			instance = new PropertiesLoader();
		return instance;
	}

	/**
	 * Load XML.
	 *
	 * @param file the file
	 */
	public static void loadXML(String file) {
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream(file));
			properties = (Properties) decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}