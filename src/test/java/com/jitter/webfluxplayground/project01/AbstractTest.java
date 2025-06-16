package com.jitter.webfluxplayground.project01;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        properties = {
                "project=project01",
//                "logging.level.org.springframework.r2dbc=DEBUG"
        }
)
public abstract class AbstractTest {

    @Test
    void contextLoads() {
    }

}
