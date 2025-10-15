package com.example.opt.domain.respository;

import com.example.opt.domain.Entity.OPT;
import com.example.opt.domain.Entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Repository
public interface QuestionRepository extends JpaRepository<OPT, String> {
    OPT findByTypeCode(String typeCode);

}