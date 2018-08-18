package net.adoptopenjdk.tests.parser.service;

import net.adoptopenjdk.tests.parser.model.Test;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TestExecutionParserImpl implements TestExecutionParser {

    final String testIsolationPattern = "--------------------------------------------------";
    final String testErrorResultPattern = "TEST RESULT: Error";
    final String testClassNamePattern = "TEST: ";

    @Override
    public List<String> process(List<String> log) {

        List<Integer> testStartList = getTestStartList(log);

        List<Test> testList = splitLogInTests(log, testStartList);

        List<Integer> testWithErrorList = getErrorIndexList(testList);

        List<String> exclusionTestList = getClassesToExclude(testList, testWithErrorList);

        return exclusionTestList;
    }

    private List<String> getClassesToExclude(List<Test> testList, List<Integer> testWithErrorList) {

        List<String> exclusionTestList = new ArrayList<>();
        for(int x = 0; x<testWithErrorList.size(); x++){

            for (String line : testList.get(x).getExecutionLines()) {

                if(line.contains(testClassNamePattern)) {
                    int from = line.indexOf(testClassNamePattern) + testClassNamePattern.length();
                    exclusionTestList.add(line.substring(from));
                    break;
                }
            }

        }
        return exclusionTestList;
    }

    private List<Integer> getErrorIndexList(List<Test> testList) {

        List<Integer> testWithErrorList = new ArrayList<>();
        for(int x = 0; x<testList.size(); x++){

            for (String line : testList.get(x).getExecutionLines()) {
                if(line.contains(testErrorResultPattern)) {
                    testWithErrorList.add(x);
                }
            }

        }
        return testWithErrorList;
    }

    private List<Test> splitLogInTests(List<String> log, List<Integer> testStartList) {

        List<Test> testList = new ArrayList<>();
        for (Iterator<Integer> iter = testStartList.iterator(); iter.hasNext(); ) {
            Integer element = iter.next();
            if (iter.hasNext()) {

                Test test = new Test(log.subList(element, iter.next()));
                testList.add(test);
            }
        }
        return testList;
    }

    private List<Integer> getTestStartList(List<String> log) {

        List<Integer> testStartList = new ArrayList<>();
        AtomicInteger index = new AtomicInteger();
        log.stream()
            .forEach( line -> {
                index.getAndIncrement();
                if(line.contains(testIsolationPattern)) {
                    testStartList.add(index.get());
                }
            });
        return testStartList;
    }
}
