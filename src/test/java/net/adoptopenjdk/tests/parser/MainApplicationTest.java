package net.adoptopenjdk.tests.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                "--issue=520",
                "--platform=linux_arm",
                "--file=sample.log"
        });

        application.run(testArguments);
        assertThat(outputCapture.toString()).contains("PARSED");
    }

    @Test
    public void passingWebAddressTest() throws Exception {

        ApplicationArguments testArguments = new DefaultApplicationArguments(new String[] {
                "--issue=520",
                "--platform=linux_arm",
                "--url=https://ci.adoptopenjdk.net/view/ev3dev/job/openjdk11_openjdktest_ev3_linux_full/lastBuild/consoleText"
        });

        application.run(testArguments);
        assertThat(outputCapture.toString()).contains("PARSED");
    }

    @Test
    public void passingMultipleFileParameters() throws Exception {

        ApplicationArguments testArguments = new DefaultApplicationArguments(new String[] {
                "--issue=520",
                "--platform=linux_arm",
                "--file=sample.log",
                "--file=sample2.log"
        });

        application.run(testArguments);
        assertThat(outputCapture.toString()).contains("NOT PARSED");
    }

    @Test
    public void passingMultipleWebAddressParameters() throws Exception {

        ApplicationArguments testArguments = new DefaultApplicationArguments(new String[] {
                "--issue=520",
                "--platform=linux_arm",
                "--url=https://ci.adoptopenjdk.net/view/ev3dev/job/openjdk11_openjdktest_ev3_linux_full/lastBuild/consoleText",
                "--url=https://ci.adoptopenjdk.net/view/ev3dev/job/openjdk11_openjdktest_ev3_linux_full/lastBuild/consoleText"
        });

        application.run(testArguments);
        assertThat(outputCapture.toString()).contains("NOT PARSED");
    }

    @Test
    public void passingMultipleMixedParameters() throws Exception {

        ApplicationArguments testArguments = new DefaultApplicationArguments(new String[] {
                "--issue=520",
                "--platform=linux_arm",
                "--file=sample.log",
                "--url=https://ci.adoptopenjdk.net/view/ev3dev/job/openjdk11_openjdktest_ev3_linux/7/consoleFull"
        });

        application.run(testArguments);
        assertThat(outputCapture.toString()).contains("NOT PARSED");
    }

}