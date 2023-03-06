package com.speakean.server.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.speakean.server.model.dto.PronunciationEval;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PronunciationService {
	
	final ETRI etri;
	
	public PronunciationEval evaluate(MultipartFile audio, String script) {
		try {
			String base64 = base64Encode(audio.getBytes());
			
			ETRI.PronunciationRequest.Argument argument = new ETRI.PronunciationRequest.Argument("korean", script, base64);
			ETRI.PronunciationRequest request = new ETRI.PronunciationRequest(argument);
			return etri.pronunciation(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String base64Encode(byte[] file) {
		String encoded = Base64.getEncoder().encodeToString(file);
		return encoded;
	}
}
