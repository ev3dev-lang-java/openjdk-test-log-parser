package net.adoptopenjdk.tests.parser.service;

import java.util.List;

public interface TestExecutionParser {

    List<String> process(List<String> log);

}
