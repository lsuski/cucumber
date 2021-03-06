package io.cucumber.gherkin;

import io.cucumber.messages.Messages.Feature;
import io.cucumber.messages.Messages.GherkinDocument;
import io.cucumber.messages.Messages.Pickle;
import io.cucumber.messages.Messages.PickleStep;
import io.cucumber.messages.Messages.Scenario;
import io.cucumber.messages.Messages.Wrapper;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class SubprocessCucumberMessagesTest {
    @Test
    public void provides_access_to_the_ast() {
        CucumberMessages cucumberMessages = new SubprocessCucumberMessages(singletonList("testdata/good/minimal.feature"), false, true, false);
        List<Wrapper> messages = cucumberMessages.messages();
        assertEquals(1, messages.size());

        // Get the AST
        GherkinDocument gherkinDocument = messages.get(0).getGherkinDocument();

        // Get the Feature node of the AST
        Feature feature = gherkinDocument.getFeature();
        assertEquals("Minimal", feature.getName());

        // Get the first Scenario node of the Feature node
        Scenario scenario = feature.getChildren(0).getScenario();
        assertEquals("minimalistic", scenario.getName());
    }

    @Test
    public void provides_access_to_pickles_which_are_compiled_from_the_ast() {
        CucumberMessages cucumberMessages = new SubprocessCucumberMessages(singletonList("testdata/good/scenario_outline.feature"), false, false, true);
        List<Wrapper> messages = cucumberMessages.messages();
        assertEquals(1, messages.size());

        // Get the first pickle
        Pickle pickle = messages.get(0).getPickle();

        // Get the first step of the pickle
        PickleStep step = pickle.getSteps(0);
        assertEquals("the minimalism", step.getText());
    }
}
