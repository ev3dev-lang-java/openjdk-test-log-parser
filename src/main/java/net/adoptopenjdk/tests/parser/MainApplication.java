package net.adoptopenjdk.tests.parser;

import net.adoptopenjdk.tests.parser.service.ReaderService;
import net.adoptopenjdk.tests.parser.service.TestExecutionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.adoptopenjdk.tests.parser.service.ReaderService.NATURE.FILE;
import static net.adoptopenjdk.tests.parser.service.ReaderService.NATURE.WEB;

@SpringBootApplication
public class MainApplication implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);

    @Autowired
    private ReaderService readerService;

    @Autowired
    private TestExecutionParser testExecutionParser;


    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        LOGGER.info("Running AdoptOpenJDK CI Log Parser");

        String strArgs = Arrays.stream(args.getSourceArgs()).collect(Collectors.joining("|"));
        LOGGER.info("Application started with arguments:" + strArgs);

        if (args.containsOption("file") || args.containsOption("url")) {

            if(args.containsOption("file")) {

                if(args.getOptionValues("file").size() == 1) {
                    List<String> log = readerService.process(args.getOptionValues("file").get(0), FILE);
                    print(testExecutionParser.process(log));
                    LOGGER.info("PARSED");
                } else {
                    LOGGER.info("NOT PARSED");
                }

            } else {

                if(args.getOptionValues("url").size() == 1) {
                    List<String> log = readerService.process(args.getOptionValues("url").get(0), WEB);
                    print(testExecutionParser.process(log));
                    LOGGER.info("PARSED");
                } else {
                    LOGGER.info("NOT PARSED");
                }

            }

        } else {
            LOGGER.info("NOT PARSED");
        }
    }

    private void print(List<String> list) {
        list.stream().forEach(System.out::println);
    }

}
