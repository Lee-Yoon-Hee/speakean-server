package com.speakean.server.model.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PronunciationEvalResponse {
	private String recognized;
	private double score;
}
