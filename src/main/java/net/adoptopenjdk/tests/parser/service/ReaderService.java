package net.adoptopenjdk.tests.parser.service;

import java.io.IOException;
import java.util.List;

public interface ReaderService {

    enum NATURE {
        FILE, WEB
    }

    List<String> process(String location, NATURE nature) throws IOException;

}
