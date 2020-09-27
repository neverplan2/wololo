package org.neverplan2.wololo.service.net.ping;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class StreamLogger implements Runnable{

    public enum StreamType{
        OUT, ERROR
    }

    private InputStream inputStream;
    private StreamType streamType;

    public void run() {
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = input.readLine()) != null) {
                if (streamType.equals(StreamType.OUT)) {
                    log.info(line);
                } else {
                    log.warn(line);
                }
            }
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
}