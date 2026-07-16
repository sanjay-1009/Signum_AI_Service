package com.signum.ai.dto;

import java.util.List;

public class PolicyAnalysisResponse {

    private String policyName;

    private String policyType;

    private String eligibility;

    private String coverage;

    private String premium;

    private List<String> benefits;

    private List<String> requiredDocuments;

    private String waitingPeriod;

    private String claimProcess;

    private List<String> exclusions;

    private List<String> advantages;

    private List<String> disadvantages;

    private List<String> whoShouldBuy;

    private String riskLevel;

    private String plainEnglishSummary;

    private String tamilSummary;

    private String hindiSummary;

    public PolicyAnalysisResponse() {
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public List<String> getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(List<String> requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    public String getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(String waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public String getClaimProcess() {
        return claimProcess;
    }

    public void setClaimProcess(String claimProcess) {
        this.claimProcess = claimProcess;
    }

    public List<String> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<String> exclusions) {
        this.exclusions = exclusions;
    }

    public List<String> getAdvantages() {
        return advantages;
    }

    public void setAdvantages(List<String> advantages) {
        this.advantages = advantages;
    }

    public List<String> getDisadvantages() {
        return disadvantages;
    }

    public void setDisadvantages(List<String> disadvantages) {
        this.disadvantages = disadvantages;
    }

    public List<String> getWhoShouldBuy() {
        return whoShouldBuy;
    }

    public void setWhoShouldBuy(List<String> whoShouldBuy) {
        this.whoShouldBuy = whoShouldBuy;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getPlainEnglishSummary() {
        return plainEnglishSummary;
    }

    public void setPlainEnglishSummary(String plainEnglishSummary) {
        this.plainEnglishSummary = plainEnglishSummary;
    }

    public String getTamilSummary() {
        return tamilSummary;
    }

    public void setTamilSummary(String tamilSummary) {
        this.tamilSummary = tamilSummary;
    }

    public String getHindiSummary() {
        return hindiSummary;
    }

    public void setHindiSummary(String hindiSummary) {
        this.hindiSummary = hindiSummary;
    }

}