package org.scoula.sample.service;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RootConfig.class })
@Log4j2
class SampleServiceTest {
    @Autowired
    private SampleService service;
    @Test
    public void doAdd() throws Exception {
        log.info(service.doAdd("123", "456"));
    }

    public class LogAdvice {
        @Before("execution(* org.scoula.sample.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
        public void logBeforeWithParam(String str1, String str2) {
            log.info("str1:" + str1);
            log.info("str2:" + str2);
        }
    }

    @Test
    public void addError() throws Exception {
        log.info(service.doAdd("123", "ABC"));
    }
}
