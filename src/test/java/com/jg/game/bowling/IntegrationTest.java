package com.jg.game.bowling;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

public class IntegrationTest {
    @Test
    public void sampleIntegrationTest () {
        String data = "./src/test/resources/sample.txt\r\n";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Match.main(null);
        } finally {
            System.setIn(stdin);
        }        
    }

    @Test
    public void perfectScoreIntegrationTest () {
        String data = "./src/test/resources/perfect_score.txt\r\n";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Match.main(null);
        } finally {
            System.setIn(stdin);
        }        
    }

    @Test
    public void zeroScoreIntegrationTest () {
        String data = "./src/test/resources/zero_score.txt\r\n";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Match.main(null);
        } finally {
            System.setIn(stdin);
        }        
    }
}
