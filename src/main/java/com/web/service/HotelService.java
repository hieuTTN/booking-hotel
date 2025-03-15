package com.web.service;

import com.web.dto.HotelDto;
import com.web.entity.*;
import com.web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {


    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelImageRepository hotelImageRepository;

    @Autowired
    private HotelUtilitiesRepository hotelUtilitiesRepository;

    public Hotel save(HotelDto dto){
        if(dto.getHotel().getId() == null){
            dto.getHotel().setNumRating(0);
            dto.getHotel().setStar(0F);
        }
        else{
            Hotel h = hotelRepository.findById(dto.getHotel().getId()).get();
            dto.getHotel().setNumRating(h.getNumRating());
            dto.getHotel().setStar(h.getStar());
        }
        Hotel result = hotelRepository.save(dto.getHotel());
        for(String s : dto.getListImage()){
            HotelImage hotelImage = new HotelImage();
            hotelImage.setImage(s);
            hotelImage.setHotel(result);
            hotelImageRepository.save(hotelImage);
        }
        hotelUtilitiesRepository.deleteByHotel(result.getId());
        for(Long id : dto.getListUtilityId()){
            HotelUtilities hotelUtilities = new HotelUtilities();
            hotelUtilities.setHotel(result);
            Utilities utilities = new Utilities();
            utilities.setId(id);
            hotelUtilities.setUtilities(utilities);
            hotelUtilitiesRepository.save(hotelUtilities);
        }
        return result;
    }

    public List<Hotel> findAll (){
        List<Hotel> result = hotelRepository.findAll();
        return result;
    }

    public Hotel findById(Long id){
        return hotelRepository.findById(id).get();
    }

    public void delete(Long id){
        hotelRepository.deleteById(id);
    }
}
