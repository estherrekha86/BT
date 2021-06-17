package com.CommonUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SerializationUtil {
	
	public static String getSerializedJson(Object obj) 
	{
		String jsonString = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = mapper.writeValueAsString(obj);
		} catch(JsonProcessingException e) {
			e.printStackTrace();
			return jsonString;
			
		}
		return jsonString;
	}

}
