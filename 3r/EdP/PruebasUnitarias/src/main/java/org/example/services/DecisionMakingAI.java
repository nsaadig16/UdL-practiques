package org.example.services;


import org.example.medicalconsultation.Suggestion;
import org.example.services.exceptions.AIException;
import org.example.services.exceptions.BadPromptException;

import java.util.List;

public interface DecisionMakingAI {
    void initDecisionMakingAI() throws AIException;

    String getSuggestions(String prompt) throws BadPromptException;

    List<Suggestion> parseSuggest(String aiAnswer);
}

