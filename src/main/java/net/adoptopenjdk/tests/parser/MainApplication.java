package net.adoptopenjdk.tests.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class MainApplication implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        LOGGER.info("Running AdoptOpenJDK CI Log Parser");

        String strArgs = Arrays.stream(args.getSourceArgs()).collect(Collectors.joining("|"));
        LOGGER.info("Application started with arguments:" + strArgs);

        String file = null;

        if (args.containsOption("file")) {
            file = args.getOptionValues("file").get(0);
        }

        LOGGER.info(file);

    }
}
