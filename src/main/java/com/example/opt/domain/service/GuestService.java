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

    public int[] findSurveyListByAge(String typeCode) {
        System.out.println("코드 : " + typeCode);
        List<Guest> allByTypeCode = guestRepository.findAllByTypeCode(typeCode);
        int[] ageList = new int[6];

        for (Guest guest : allByTypeCode) {
            System.out.println("번호 : " +guest.getRandomNumber());
            int index = (guest.getAge() / 10) - 1;
            // 60세 이상은 기타로 분류
            if (index > 5)
                index = 6;

            ageList[index]++;
        }

        return ageList;
    }
}

