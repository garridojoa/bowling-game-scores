package com.jg.game.bowling;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Integration tests.
 * @author jgarrido
 * @date 2021/03/07
 */
@Category(IntegrationTest.class)
public class FullIntegrationTest {
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

    @Test
    public void defaultFileIntegrationTest () {
        String data = "\r\n";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Match.main(null);
        } finally {
            System.setIn(stdin);
        }        
    }

    @Test
    public void fileNotFoundIntegrationTest () {
        String data = "./src/test/resources/zero_score_.txt\r\n";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Match.main(null);
        } finally {
            System.setIn(stdin);
        }        
    }

    @Test
    public void invalidPinfallIntegrationTest () {
        String data = "./src/test/resources/invalid_pinfall.txt\r\n";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Match.main(null);
        } finally {
            System.setIn(stdin);
        }        
    }

    @Test
    public void invalidFileDataIntegrationTest () {
        String data = "./src/test/resources/invalid_file_data.txt\r\n";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Match.main(null);
        } finally {
            System.setIn(stdin);
        }        
    }
}
