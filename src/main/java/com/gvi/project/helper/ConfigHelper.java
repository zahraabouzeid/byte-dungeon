package com.gvi.project.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gvi.project.models.core.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConfigHelper {
	private static final Logger log = LoggerFactory.getLogger(ConfigHelper.class);
	private static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * Loads a typed config object from the classpath so runtime and tests resolve
	 * the same resource path through the owning config class.
	 */
	public static <T extends Config> T getConfig(Class<T> configClass, String filePath) {
		log.debug("Loading config {} from {}.", configClass.getSimpleName(), filePath);

		try (InputStream iStream = configClass.getResourceAsStream(filePath)) {

			if (iStream == null) {
				log.error("Config resource not found: {} for {}.", filePath, configClass.getSimpleName());
				throw new RuntimeException("File not found in resources: %s%n".formatted(filePath));
			}

			T config = mapper.readValue(iStream, configClass);
			log.debug("Loaded config {} from {}.", configClass.getSimpleName(), filePath);
			return config;
		} catch (IOException e) {
			log.error("Failed to parse config {} from {}.", configClass.getSimpleName(), filePath, e);
			throw new RuntimeException("Error while parsing file: " + filePath, e);
		}
	}
}
