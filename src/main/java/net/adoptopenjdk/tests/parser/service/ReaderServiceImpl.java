package net.adoptopenjdk.tests.parser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReaderServiceImpl implements ReaderService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ReaderServiceImpl.class);

    @Override
    public List<String> process(final String location, NATURE nature) throws IOException {

        if(nature.equals(NATURE.FILE)) {
            return processFile(location);
        } else {
            return processWebAddress(location);
        }
    }

    private List<String> processFile(final String location) throws IOException {

        List<String> fileContent = new ArrayList<>();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(location).getFile());

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            fileContent.add(line);
        }

        return fileContent;
    }

    private List<String> processWebAddress(final String location) throws IOException {

        List<String> fileContent = new ArrayList<>();

        URL oracle = new URL(location);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String line;
        while ((line = in.readLine()) != null){
            fileContent.add(line);
        }
        in.close();

        return fileContent;
    }
}
