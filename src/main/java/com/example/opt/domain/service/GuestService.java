package com.example.opt.domain.service;

import com.example.opt.domain.Entity.Guest;
import com.example.opt.domain.respository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;

    public void save(String gender, int age, String typeCode){
        Guest guest = new Guest(gender, age, typeCode);
        guestRepository.save(guest);
    }
}
