package com.example.opt.domain.Entity;

import com.example.opt.global.config.StringListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OPT {
    @Id
    private String typeCode;
    private String typeName;
    @Convert(converter = StringListConverter.class)
    @Column(length = 1024) // 넉넉한 길이 설정
    private List<String> hashTags;
    private String summary;
    private String description;
}
