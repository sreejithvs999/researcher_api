package com.svs.rch.user.core.common.data;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LocalDateSerializer extends StdSerializer<LocalDate> implements ContextualSerializer {

	private static final long serialVersionUID = 2421272196294010174L;

	private DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

	public LocalDateSerializer() {
		super(LocalDate.class);
	}

	@Override
	public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString(value.format(dateFormatter));
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
			throws JsonMappingException {

		LocalDateSerializer localDateSer = new LocalDateSerializer();
		DateTimeFormat dtf = property.getAnnotation(DateTimeFormat.class);
		if (dtf != null) {
			localDateSer.dateFormatter = DateTimeFormatter.ofPattern(dtf.pattern());
		}
		return localDateSer;
	}

}
