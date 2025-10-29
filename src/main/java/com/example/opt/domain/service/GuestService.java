package com.example.opt.domain.service;

import com.example.opt.domain.Entity.Guest;
import com.example.opt.domain.respository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;

    public void save(String gender, int age, String typeCode) {
        Guest guest = new Guest(gender, age, typeCode);
        guestRepository.save(guest);
    }

    public int findTotalCount() {
        return guestRepository.findTotalCount();
    }
    public int findTotalCountByType(String typeCode) {
        return guestRepository.findAllByTypeCode(typeCode).size();
    }
    public int findPercentOfGender(String gender, int totalCount) {
        int count = guestRepository.findTotalGender(gender);
        return count * 100 / totalCount;
    }
    public int[] findSurveyListByAge(String typeCode) {
        List<Guest> allByTypeCode = guestRepository.findAllByTypeCode(typeCode);

        // 전체 Guest의 수를 구합니다.
        int totalCount = allByTypeCode.size();

        // 나이대별 인원수를 셀 배열 (10대, 20대, 30대, 40대, 50대, 60대 이상)
        int[] ageCount = new int[6];

        for (Guest guest : allByTypeCode) {
            int age = guest.getAge();
            int index = (age / 10) - 1;

            // 60세 이상은 index 5로 분류
            if (index < 0) { // 10세 미만
                index = 5;
            } else if (index > 4) { // 60세 이상
                index = 5;
            }

            ageCount[index]++;
        }

        int[] ageRatio = new int[6];

        if (totalCount > 0) {
            for (int i = 0; i < ageCount.length; i++) {
                // (나이대별 인원수 / 전체 인원수) * 100.0 (소수점 계산을 위해 100.0을 사용)
                double ratio = ((double) ageCount[i] / totalCount) * 100.0;

                ageRatio[i] = (int) ratio;
            }
        }

        return ageRatio;
    }
}

