package net.adoptopenjdk.tests.parser.service;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Component
public class LogWebAddressService {

    public void process(final String address) throws IOException {

        URL oracle = new URL(address);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null){
            System.out.println(inputLine);
        }
        in.close();

    }
}
