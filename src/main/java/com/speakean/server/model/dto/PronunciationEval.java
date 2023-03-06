package com.speakean.server.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PronunciationEval {
	String recognized;
	double score;
}