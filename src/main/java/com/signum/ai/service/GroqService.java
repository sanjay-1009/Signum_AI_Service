package com.signum.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GroqService {

    private final ChatClient chatClient;

    public GroqService(ChatClient.Builder builder) {

        this.chatClient = builder.build();

    }

    public String askAI(String question) {

    	String prompt = """
    			You are Signum AI, the official AI assistant for the Signum Insurance Claim Processing System.

    			Instructions:
    			- Reply in clean, attractive bullet points.
    			- NEVER use Markdown (*, -, #, **).
    			- Use the Unicode bullet symbol "•".
    			- Add suitable emojis where appropriate.
    			- Maximum 5 points.
    			- Keep each point under 12 words.
    			- Use simple English.
    			- No paragraphs.
    			- If greeting, greet briefly.

    			Example format:

    			🚗 Vehicle Insurance

    			• Covers accidental vehicle damage.
    			• Includes third-party liability.
    			• Requires a valid driving license.
    			• Carry policy documents.
    			• Contact support for claim assistance.

    			User Question:
    			""" + question;

        return chatClient
                .prompt(prompt)
                .call()
                .content();

    }

}