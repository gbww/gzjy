package com.gzjy.contract.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonDateSerializer extends JsonDeserializer<Date> {
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {    
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
                String date = jp.getText();    
                try {    
                    return format.parse(date);    
                } catch (Exception e) {    
                    throw new RuntimeException(e);    
                }    
    }    
}
