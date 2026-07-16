package com.signum.ai.service;

import com.signum.ai.model.IntentType;
import org.springframework.stereotype.Service;
import com.signum.ai.model.ChatRequest;
import com.signum.ai.model.ChatResponse;
import com.signum.ai.model.KnowledgeType;
import java.util.Map;
import com.signum.ai.service.DatabaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.signum.ai.dto.PolicyAnalysisResponse;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class AIService {

    private final IntentService intentService;
    private final RAGService ragService;
    private final GroqService groqService;
    private final KnowledgeRouter knowledgeRouter;
    private final DatabaseService databaseService;
    

    public AIService(
            IntentService intentService,
            RAGService ragService,
            GroqService groqService,KnowledgeRouter knowledgeRouter, DatabaseService databaseService) {

        this.intentService = intentService;
        this.ragService = ragService;
        this.groqService = groqService;
        this.knowledgeRouter = knowledgeRouter;
        this.databaseService = databaseService;

    }

    public ChatResponse askAI(ChatRequest request) {

    	IntentType intent =
    	        intentService.detectIntent(request.getMessage());

        StringBuilder prompt =
                new StringBuilder();

        prompt.append("""

        		You are Signum AI.

        		You are NOT a general chatbot.

        		You ONLY answer questions about the Signum Insurance Claim Processing System.

        		Rules

        		• Never invent features.

        		• Never mention websites.

        		• Never mention customer support.

        		• Never say "coming soon" unless the knowledge explicitly says so.

        		• If the answer exists in Signum Knowledge,
        		answer ONLY from that knowledge.

        		• Reply using 3-6 bullet points.

        		• Every bullet must contain one sentence.

        		• Keep answers short.

        		""");
        
        System.out.println("================================");
        System.out.println("Question : " + request.getMessage());
        System.out.println("Intent   : " + intent);
        System.out.println("================================");

        switch (intent) {

            case KNOWLEDGE -> {

            	String knowledge =
            	        ragService.searchKnowledge(
            	                request.getMessage(),
            	                "SignumKnowledge.txt");

            	prompt.append("""

            	Application Knowledge:

            	""");

            	prompt.append(knowledge);

                prompt.append("\nSignum Knowledge\n\n");

                prompt.append(knowledge);

            }

            case DATABASE -> {

                int userId = request.getUserId();

                String username =
                        databaseService.getUserName(userId);

                int totalPolicies =
                        databaseService.getPolicyCount(userId);

                int totalClaims =
                        databaseService.getClaimCount(userId);

                int approved =
                        databaseService.getApprovedClaims(userId);

                int rejected =
                        databaseService.getRejectedClaims(userId);

                int pending =
                        databaseService.getPendingClaims(userId);

                Map<String,Object> latestClaim =
                        databaseService.latestClaim(userId);

                prompt.append("""

            You are Signum AI.

            Below is LIVE database information.

            Use ONLY this information.

            Never invent any data.

            Reply in short bullet points.

            Maximum 5 bullets.

            """);

                prompt.append("\nUsername : ");
                prompt.append(username);

                prompt.append("\nTotal Policies : ");
                prompt.append(totalPolicies);

                prompt.append("\nTotal Claims : ");
                prompt.append(totalClaims);

                prompt.append("\nApproved : ");
                prompt.append(approved);

                prompt.append("\nRejected : ");
                prompt.append(rejected);

                prompt.append("\nPending : ");
                prompt.append(pending);

                if(latestClaim != null){

                    prompt.append("\nLatest Claim : ");
                    prompt.append(latestClaim.toString());

                }

            }

            case GENERAL -> {

            	prompt.append("""

            			You are Signum AI.

            			You are an intelligent assistant for the Signum Insurance Claim Processing System.

            			Strict Rules

            			Only answer using the supplied knowledge.

            			Never invent new Signum features.

            			If the answer exists in the knowledge,
            			use only that information.

            			Do not mention websites.

            			Do not mention customer support.

            			Keep replies below 5 points.

            			Each point should contain one sentence.

            			Be friendly and professional.

            			If knowledge is empty,

            			simply reply

            			"I couldn't find this in Signum. Please ask about policies, claims, dashboard or insurance."

            			Knowledge:

            			""");


            }

        }

        prompt.append("\n\nUser Question:\n");

        prompt.append(request.getMessage());

        String answer =
                groqService.askAI(prompt.toString());

        return new ChatResponse(

                answer,

                "Signum Knowledge",

                intent.name()

        );

    }
    public PolicyAnalysisResponse analyzePolicy(String policyText) {

        try {

            String prompt = """
    You are Signum AI Advisor.

    You are given the complete Terms & Conditions of an insurance policy.

    Your task is to extract the information.

    Rules

    Return ONLY valid JSON.

    Do not explain anything.

    Do not use markdown.

    If a value is unavailable, write "Not Available".

    Return exactly this structure:

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

    Policy Document:

    """ + policyText;

            String aiResponse =
                    groqService.askAI(prompt);

            ObjectMapper mapper =
                    new ObjectMapper();
            
            System.out.println("========= GROQ RESPONSE ==========");
            System.out.println(aiResponse);
            System.out.println("==================================");

            // Extract only the JSON object
            int start = aiResponse.indexOf("{");
            int end = aiResponse.lastIndexOf("}");

            if (start == -1 || end == -1 || end <= start) {
                throw new RuntimeException("AI did not return valid JSON.");
            }

            /*String json = aiResponse.substring(start, end + 1);

            System.out.println("========= JSON ==========");
            System.out.println(json);
            System.out.println("=========================");

            

            return mapper.readValue(json, PolicyAnalysisResponse.class);*/
            
            String json = aiResponse.substring(start, end + 1);

            System.out.println("========== JSON ==========");
            System.out.println(json);
            System.out.println("==========================");

            JsonNode node = mapper.readTree(json);

            System.out.println("policyName = " + node.get("policyName"));
            System.out.println("policyType = " + node.get("policyType"));
            System.out.println("coverage = " + node.get("coverage"));

            PolicyAnalysisResponse response =
                    mapper.treeToValue(node, PolicyAnalysisResponse.class);

            System.out.println("Mapped Policy Name = " + response.getPolicyName());

            return response;

        }

        catch(Exception e){

            e.printStackTrace();

        }

        return new PolicyAnalysisResponse();

    }

}