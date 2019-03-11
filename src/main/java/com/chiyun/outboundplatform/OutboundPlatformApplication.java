package com.chiyun.outboundplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class OutboundPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutboundPlatformApplication.class, args);
	}

	@Bean
	public Converter<String, Date> addNewConvert() {
		return new Converter<String, Date>() {
			@Override
			public Date convert(String source) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = sdf.parse((String) source);
				} catch (ParseException e) {
				}
				if (date == null) {
					try {
						date = sdf2.parse((String) source);
					} catch (ParseException e) {
						return null;
					}
				}
				return date;
			}
		};
	}
}
