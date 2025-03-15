package com.web.api;

import com.web.dto.HotelDto;
import com.web.entity.Hotel;
import com.web.entity.Room;
import com.web.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelApi {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/admin/create")
    public ResponseEntity<?> save(@RequestBody HotelDto hotelDto){
        Hotel result = hotelService.save(hotelDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        hotelService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/public/findAll")
    public ResponseEntity<?> findAll(){
        List<Hotel> result = hotelService.findAll();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/admin/findById")
    public ResponseEntity<?> findById(@RequestParam("id") Long id){
        Hotel result = hotelService.findById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/public/findById")
    public ResponseEntity<?> findByIdUser(@RequestParam("id") Long id){
        Hotel result = hotelService.findById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
