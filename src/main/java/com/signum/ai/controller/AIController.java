package com.signum.ai.controller;
import com.signum.ai.model.ChatRequest;
import com.signum.ai.model.ChatResponse;

import com.signum.ai.service.AIService;
import com.signum.ai.service.GroqService;
import org.springframework.web.bind.annotation.*;

import com.signum.ai.dto.PolicyAnalysisRequest;
import com.signum.ai.dto.PolicyAnalysisResponse;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "http://localhost:3000")
public class AIController {

	private final AIService aiService;

	public AIController(AIService aiService) {

	    this.aiService = aiService;

	}

    @GetMapping("/test")
    public String test() {

        return "Signum AI Running";

    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {

        return aiService.askAI(request);

    }
    @PostMapping("/analyze-policy")
    public PolicyAnalysisResponse analyzePolicy(
            @RequestBody PolicyAnalysisRequest request) {

        return aiService.analyzePolicy(
                request.getPolicyText());

    }
    

}