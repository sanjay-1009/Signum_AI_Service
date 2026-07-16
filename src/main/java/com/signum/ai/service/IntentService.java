package com.signum.ai.service;

import com.signum.ai.model.IntentType;
import org.springframework.stereotype.Service;

@Service
public class IntentService {

    public IntentType detectIntent(String question) {

        question = question.toLowerCase();

        // =========================
        // DATABASE QUESTIONS
        // =========================

        if (
                question.contains("my policy")
                || question.contains("my policies")
                || question.contains("my claim")
                || question.contains("my claims")
                || question.contains("latest claim")
                || question.contains("claim status")
                || question.contains("status")
                || question.contains("approved")
                || question.contains("rejected")
                || question.contains("pending")
                || question.contains("how many policies")
                || question.contains("how many claims")
                || question.contains("list my policies")
                || question.contains("show my policies")
                || question.contains("show my claims")
                || question.contains("do i have")
        ) {

            return IntentType.DATABASE;

        }

        // =========================
        // KNOWLEDGE QUESTIONS
        // =========================

        if (
                question.contains("submit claim")
                || question.contains("how to submit")
                || question.contains("login")
                || question.contains("dashboard")
                || question.contains("health insurance")
                || question.contains("vehicle insurance")
                || question.contains("travel insurance")
                || question.contains("deductible")
                || question.contains("premium")
                || question.contains("coverage")
                || question.contains("policy meaning")
        ) {

            return IntentType.KNOWLEDGE;

        }

        return IntentType.GENERAL;

    }

}