package com.example.dmfp.domain.respository;

import com.example.dmfp.domain.Entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Slf4j
public class QuestionRepository {
    private List<Question> questions = new ArrayList(Arrays.asList(



    ));
}
