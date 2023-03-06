package com.speakean.server.service;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.speakean.server.model.dto.PronunciationEval;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
public class ETRI {
	static final String pronunciationURL = "http://aiopen.etri.re.kr:8000/WiseASR/PronunciationKor";
	static final String authorization = "1cc0528b-7f5d-4de8-a75e-8a64b485daf8";
	
	final ObjectMapper objectMapper;
	
	PronunciationEval pronunciation(PronunciationRequest request) {
		try {
			String json = objectMapper.writeValueAsString(objectMapper.convertValue(request, Map.class));
			
			URL url = new URL(pronunciationURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Authorization", authorization);
			
			DataOutputStream dos = new DataOutputStream(con.getOutputStream());
			dos.write(json.getBytes("UTF-8"));
			dos.flush();
			dos.close();
			
			InputStream is = con.getInputStream();
			byte [] buffer = new byte[is.available()];
			is.read(buffer);
			
			PronunciationResponse response = objectMapper.readValue(buffer, PronunciationResponse.class);
			if(response.result != 0) {
				return null;
			}
			
			PronunciationEval eval = new PronunciationEval();
			eval.setRecognized(response.getReturn_object().getRecognized());
			eval.setScore(Double.parseDouble(response.getReturn_object().getScore()));
			return eval;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Data
	static class PronunciationRequest {
		@Data
		static class Argument {
			final String language_code;
			final String script;
			final String audio;
		}
		
		final Argument argument;	
	}
	
	@Data
	@NoArgsConstructor
	static class PronunciationResponse {
		@Data
		static class ReturnObject {
			String recognized;
			String score;
		}
		
		Integer result;
		ReturnObject return_object;
	}
}



