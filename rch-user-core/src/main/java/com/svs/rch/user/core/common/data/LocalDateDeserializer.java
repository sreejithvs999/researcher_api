package com.svs.rch.user.core.common.data;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> implements ContextualDeserializer {

	private static final long serialVersionUID = 6892708748862629071L;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

	public LocalDateDeserializer() {
		super(LocalDate.class);
	}

	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return LocalDate.parse(p.readValueAs(String.class), dateFormatter);
	}

	@Override
	public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
			throws JsonMappingException {

		LocalDateDeserializer localDateDeser = new LocalDateDeserializer();
		DateTimeFormat dtf = property.getAnnotation(DateTimeFormat.class);
		if (dtf != null) {
			localDateDeser.dateFormatter = DateTimeFormatter.ofPattern(dtf.pattern());
		}
		return localDateDeser;
	}

}
