package com.speakean.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.speakean.server.model.dto.PronunciationEval;
import com.speakean.server.model.dto.response.PronunciationEvalResponse;
import com.speakean.server.service.PronunciationService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/pronunciation")
@RequiredArgsConstructor
public class PronunciationController {
	
	final PronunciationService pronunciationService;
	
	@GetMapping("/")
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("Hello");
	}
	
	@PostMapping("evaluate")
	public ResponseEntity<PronunciationEvalResponse> evaluate(@RequestPart("audio") MultipartFile audio, @RequestPart(value="script", required=false) String script) {
		PronunciationEval eval = pronunciationService.evaluate(audio, script);
		
		if(eval == null) {
			return ResponseEntity.status(500).build();
		} else {
			PronunciationEvalResponse response = new PronunciationEvalResponse();
			response.setRecognized(eval.getRecognized());
			response.setScore(eval.getScore());
			
			return ResponseEntity.ok(response);
		}
	}
}
