package net.adoptopenjdk.tests.parser.model;

import java.util.List;

public class Test {

    List<String> executionLines;

    public Test(List<String> executionLines) {
        this.executionLines = executionLines;
    }

    public List<String> getExecutionLines() {
        return executionLines;
    }
}
