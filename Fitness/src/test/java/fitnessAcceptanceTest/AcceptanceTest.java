package fitnessAcceptanceTest;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "Features",  // Path to your feature files
    plugin = {
        "pretty",
        "html:target/cucumber-html-report",
        "json:target/cucumber.json",
        "junit:target/cucumber.xml"
    },
    monochrome = true,
    snippets = SnippetType.CAMELCASE,
    glue = {"fitnessAcceptanceTest"}  // Package where your step definitions are
)
public class AcceptanceTest {
}
