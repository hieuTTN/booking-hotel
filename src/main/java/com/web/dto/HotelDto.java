package com.web.dto;

import com.web.entity.Hotel;
import com.web.entity.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HotelDto {

    private Hotel hotel;

    private List<String> listImage = new ArrayList<>();

    private List<Long> listUtilityId = new ArrayList<>();
}
