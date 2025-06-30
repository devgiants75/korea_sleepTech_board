package com.project.board_back.controller;

import com.project.board_back.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
@CrossOrigin(origins = "http://localhost:3000")
public class GeminiController {
    private final GeminiService geminiService;
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/ask")
    public ResponseEntity<ResponseDto> ask(@RequestBody QueryDto req) {
        String answer = geminiService.askGemini(req.getInput());
        return ResponseEntity.ok(new ResponseDto(answer));
    }
}
