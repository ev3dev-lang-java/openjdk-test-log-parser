package net.adoptopenjdk.tests.parser;

import net.adoptopenjdk.tests.parser.service.LogFileService;
import net.adoptopenjdk.tests.parser.service.LogWebAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class MainApplication implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);

    @Autowired
    private LogFileService logFileService;

    @Autowired
    private LogWebAddressService logWebAddressService;
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        LOGGER.info("Running AdoptOpenJDK CI Log Parser");

        String strArgs = Arrays.stream(args.getSourceArgs()).collect(Collectors.joining("|"));
        LOGGER.info("Application started with arguments:" + strArgs);

        if(args.containsOption("file") || args.containsOption("url")) {

            if(args.containsOption("file")) {

                if(args.getOptionValues("file").size() == 1) {
                    logFileService.process(args.getOptionValues("file").get(0));
                } else {
                    LOGGER.info("NOT PARSED");
                }

            } else {

                if(args.getOptionValues("url").size() == 1) {
                    logWebAddressService.process(args.getOptionValues("url").get(0));
                } else {
                    LOGGER.info("NOT PARSED");
                }

            }

        } else {
            LOGGER.info("NOT PARSED");
        }
    }

}
