package org.example.doubles;

import org.example.data.ProductID;
import org.example.medicalconsultation.FqUnit;
import org.example.medicalconsultation.Suggestion;
import org.example.medicalconsultation.dayMoment;
import org.example.services.DecisionMakingAI;
import org.example.services.exceptions.AIException;
import org.example.services.exceptions.BadPromptException;

import java.util.ArrayList;
import java.util.List;

public class DecisionMakingAIMock implements DecisionMakingAI {

    private boolean throwAIException;
    private boolean throwBadPromptException;

    public DecisionMakingAIMock() {
        this.throwAIException = false;
        this.throwBadPromptException = false;
    }

    @Override
    public void initDecisionMakingAI() throws AIException {
        if (throwAIException) {
            throw new AIException("The AI couldn't be invoked correctly");
        }
    }

    @Override
    public String getSuggestions(String prompt) throws BadPromptException {
        if (throwBadPromptException) {
            throw new BadPromptException("The prompt wasn't clear enough");
        }
        if (prompt == null || prompt.isBlank()) {
            throw new BadPromptException("Prompt can't be empty");
        }
        return "I," + "0".repeat(10) + ",BEFORELUNCH,15,1,1,DAY,Drink with abundant water\n" +
                "R," + "1".repeat(10) + "\n" +
                "M," + "2".repeat(10) + ",,,3,,,"
                ;
    }

    @Override
    public List<Suggestion> parseSuggest(String aiAnswer) {
        List<Suggestion> l = new ArrayList<>();
        l.add(new Suggestion.Insert(
                new ProductID("0".repeat(10)),
                dayMoment.BEFORELUNCH,
                15,
                1,
                1,
                FqUnit.DAY,
                "Drink with abundant water"
        ));
        l.add(new Suggestion.Remove(
                new ProductID("1".repeat(10))
        ));
        l.add(new Suggestion.Modify(
                new ProductID("2".repeat(10)),
                null, 0, 3, 0, null, null
        ));
        return l;
    }

    public void setThrowAIException(boolean b) {
        this.throwAIException = b;
    }

    public void setThrowBadPromptException(boolean b) {
        this.throwBadPromptException = b;
    }
}
