package com.signum.ai.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class RAGService {

    private final Map<String, String> knowledgeBase = new HashMap<>();

    @PostConstruct
    public void loadKnowledge() {

        loadFile("SignumKnowledge.txt");
        loadFile("FAQ.txt");
        loadFile("UserGuide.txt");
        loadFile("ClaimGuide.txt");
        loadFile("InsuranceTerms.txt");

        System.out.println("=================================");
        System.out.println("Signum Knowledge Base Loaded");
        System.out.println("Documents : " + knowledgeBase.size());
        System.out.println("=================================");
    }

    private void loadFile(String fileName) {

        try {

            ClassPathResource resource =
                    new ClassPathResource("knowledge/" + fileName);

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(resource.getInputStream()));

            StringBuilder content =
                    new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {

                content.append(line)
                        .append("\n");

            }

            knowledgeBase.put(fileName, content.toString());

        }

        catch (Exception e) {

            System.out.println(fileName + " not found.");

        }

    }

    public String searchKnowledge(String question, String fileName) {

        String document = knowledgeBase.get(fileName);

        if(document == null)
            return "";

        String lowerDocument = document.toLowerCase();
        String lowerQuestion = question.toLowerCase();

        int score = 0;

        for(String word : lowerQuestion.split("\\s+")){

            if(word.length() < 3)
                continue;

            if(lowerDocument.contains(word))
                score++;

        }

        if(score == 0)
            return "";

        return document;
    }

}