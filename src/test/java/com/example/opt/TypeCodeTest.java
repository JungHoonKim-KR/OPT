package com.example.opt;

import com.example.opt.domain.Entity.OPT;
import com.example.opt.domain.respository.OPTRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TypeCodeTest {
    @Autowired
    private OPTRepository questionRepository;

    @Test
    void test() {
        OPT defa = questionRepository.findByTypeCode("DEFA");
        System.out.println(defa.getRoutineSuggestion());
    }
}
