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

    public void save(String gender, int age, String typeCode){
        Guest guest = new Guest(gender, age, typeCode);
        guestRepository.save(guest);
    }

    public int findTotalCount(){
        return guestRepository.findTotalCount();
    }

    public int[] findSurveyListByAge(String typeCode){
        List<Guest> allByTypeCode = guestRepository.findAllByTypeCode(typeCode);
        int[] ageList = new int[6];

        for(Guest guest : allByTypeCode){
            int index = (guest.getAge() / 10) -1;

            if(index >=0 && index<=5){
                ageList[index]++;
            }
        }
        return ageList;
    }

}
