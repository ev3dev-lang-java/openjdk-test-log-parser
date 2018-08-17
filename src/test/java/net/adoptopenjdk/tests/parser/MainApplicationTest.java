package net.adoptopenjdk.tests.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainApplicationTest {

    @Autowired
    private MainApplication application;

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void passingNoArgumentTest() throws Exception {

        ApplicationArguments testArguments = new DefaultApplicationArguments(new String[] {});

        application.run(testArguments);
        assertThat(outputCapture.toString()).contains("NOT PARSED");
    }

    @Test
    public void passingLocalFileTest() throws Exception {

        ApplicationArguments testArguments = new DefaultApplicationArguments(new String[] {
                "--file=sample.log"
        });

        application.run(testArguments);
        assertThat(outputCapture.toString()).contains("PARSED");
    }

    @Test
    public void passingWebAddressTest() throws Exception {

        ApplicationArguments testArguments = new DefaultApplicationArguments(new String[] {
                "--url=https://ci.adoptopenjdk.net/view/ev3dev/job/openjdk11_openjdktest_ev3_linux/lastBuild/consoleText"
        });

        application.run(testArguments);
        assertThat(outputCapture.toString()).contains("PARSED");
    }

    @Test
    public void passingMultipleFileParameters() throws Exception {

        ApplicationArguments testArguments = new DefaultApplicationArguments(new String[] {
                "--file=sample.log",
                "--file=sample2.log"
        });

        application.run(testArguments);
        assertThat(outputCapture.toString()).contains("NOT PARSED");
    }

    @Test
    public void passingMultipleWebAddressParameters() throws Exception {

        ApplicationArguments testArguments = new DefaultApplicationArguments(new String[] {
                "--url=https://ci.adoptopenjdk.net/view/ev3dev/job/openjdk11_openjdktest_ev3_linux/7/consoleFull",
                "--url=https://ci.adoptopenjdk.net/view/ev3dev/job/openjdk11_openjdktest_ev3_linux/7/consoleFull"
        });

        application.run(testArguments);
        assertThat(outputCapture.toString()).contains("NOT PARSED");
    }

    @Test
    public void passingMultipleMixedParameters() throws Exception {

        ApplicationArguments testArguments = new DefaultApplicationArguments(new String[] {
                "--file=sample.log",
                "--url=https://ci.adoptopenjdk.net/view/ev3dev/job/openjdk11_openjdktest_ev3_linux/7/consoleFull"
        });

        application.run(testArguments);
        assertThat(outputCapture.toString()).contains("NOT PARSED");
    }

}