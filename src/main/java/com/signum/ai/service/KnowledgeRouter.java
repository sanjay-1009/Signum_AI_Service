package com.signum.ai.service;

import com.signum.ai.model.KnowledgeType;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeRouter {

    public KnowledgeType detectKnowledge(String question) {

        question = question.toLowerCase();

        if(question.contains("claim")
                || question.contains("status")
                || question.contains("approved")
                || question.contains("rejected")
                || question.contains("pending")
                || question.contains("incident")) {

            return KnowledgeType.CLAIM_GUIDE;

        }

        if(question.contains("policy")
                || question.contains("dashboard")
                || question.contains("login")
                || question.contains("submit")) {

            return KnowledgeType.USER_GUIDE;

        }

        if(question.contains("premium")
                || question.contains("coverage")
                || question.contains("deductible")
                || question.contains("insurance")
                || question.contains("vehicle")
                || question.contains("health")) {

            return KnowledgeType.INSURANCE_TERMS;

        }

        if(question.contains("signum")) {

            return KnowledgeType.SIGNUM;

        }

        return KnowledgeType.FAQ;

    }

}