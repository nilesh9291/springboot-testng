/*package com.rest.userapp.utils;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;


public class EncryptedPropertyListener implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final Logger logger = LoggerFactory.getLogger(EncryptedPropertyListener.class);

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		
		for (PropertySource<?> propertySource : environment.getPropertySources()) {
			Map<String, Object> propertyOverrides = new LinkedHashMap<String, Object>();
			decodePasswords(propertySource, propertyOverrides);
			
			if (!propertyOverrides.isEmpty()) {
				PropertySource<?> decodedProperties = new MapPropertySource("decoded " + propertySource.getName(),
						propertyOverrides);
				environment.getPropertySources().addBefore(propertySource.getName(), decodedProperties);

			}
		}

	}

	private void decodePasswords(PropertySource<?> source, Map<String, Object> propertyOverrides) {
		if (source instanceof EnumerablePropertySource) {
			EnumerablePropertySource<?> enumerablePropertySource = (EnumerablePropertySource<?>) source;
			
			for (String key : enumerablePropertySource.getPropertyNames()) {
				Object rawValue = source.getProperty(key);
				
				if (rawValue instanceof String) {
					String input = (String) rawValue;
					
					if (key.contains("password")) {
						String decodedValue = null;
					
						try {
							decodedValue = EncodeDecodeUtil.decode(input);
							propertyOverrides.put(key, decodedValue);
						} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
								| BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException
								| IOException e) {
							logger.error("Exception during password decryption : " + e.getMessage());
						}

					}
				}
			}
		}
	}
}
*/