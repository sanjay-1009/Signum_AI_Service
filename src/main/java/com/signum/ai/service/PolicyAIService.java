package com.signum.ai.service;

import org.springframework.stereotype.Service;

@Service
public class PolicyAIService {

    private final GroqService groqService;

    public PolicyAIService(GroqService groqService) {

        this.groqService = groqService;

    }

    public String analyzePolicy(String policyText) {

    	String prompt = """
    			You are an Insurance Policy Extraction Engine.

    			Your job is ONLY to extract information.

    			IMPORTANT RULES:

    			1. Return ONLY valid JSON.
    			2. Do NOT say Hi.
    			3. Do NOT explain.
    			4. Do NOT use bullet points.
    			5. Do NOT use markdown.
    			6. Do NOT use ```json.
    			7. Do NOT write any extra text.
    			8. Every field must exist.
    			9. If information is missing, write "Not Available".

    			Return EXACTLY this format:

    			{
    			  "policyName":"",
    			  "policyType":"",
    			  "eligibility":"",
    			  "coverage":"",
    			  "premium":"",
    			  "benefits":[],
    			  "requiredDocuments":[],
    			  "waitingPeriod":"",
    			  "claimProcess":"",
    			  "exclusions":[],
    			  "advantages":[],
    			  "disadvantages":[],
    			  "whoShouldBuy":[],
    			  "riskLevel":"",
    			  "plainEnglishSummary":"",
    			  "tamilSummary":"",
    			  "hindiSummary":""
    			}

    			Insurance Policy:

    			""" + policyText;

        return groqService.askAI(prompt);

    }

}