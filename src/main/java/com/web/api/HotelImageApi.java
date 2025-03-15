package com.web.api;
import com.web.repository.HotelImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel-image")
@CrossOrigin
public class HotelImageApi {

    @Autowired
    private HotelImageRepository hotelImageRepository;

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        hotelImageRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
