package com.example.jpamanytoone.controller;

import com.example.jpamanytoone.model.Kommune;
import com.example.jpamanytoone.model.Region;
import com.example.jpamanytoone.repositoryKommuner.RepositoryKommuner;
import com.example.jpamanytoone.serviceKommuner.ApiServiceGetKommuner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class KommuneRestController {

    @Autowired
    ApiServiceGetKommuner apiServiceGetKommuner;
    @Autowired
    RepositoryKommuner repositoryKommuner;

    @GetMapping("getkommune")
    public List<Kommune> getKommune(){
        List<Kommune> listKommune = apiServiceGetKommuner.getKomuner();
        return listKommune;
    }

    // her ville vi gerne havde en metode som opdater en bestemt kommune ud fra et ID.
    @PutMapping("/kommune/{id}")
    public ResponseEntity<Kommune> updateKommune(@PathVariable("id") String id, @RequestBody Kommune updateKommune) {
        // her under laver vi optional<kommune> den kan være tom uden den giver en fejlkode.
        // her efter giver vi den et navn kommuneData, og gør den = med et bestem id fra repositoryKommuner.
        Optional<Kommune> kommuneData = repositoryKommuner.findById(id);
        // her under bruger vi Optinal (.isPresent()) vi tjekker om værdien er tom.
        if (kommuneData.isPresent()) {
            Kommune kommune = kommuneData.get();
            // Brug navn fra updateKommune i stedet for hardcoded værdi
            kommune.setNavn(updateKommune.getNavn());
            // nu indeholder kummune updateKommune og genmer det nede under.
            Kommune update = repositoryKommuner.save(kommune);
            return new ResponseEntity<>(update, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/kommune")
    public ResponseEntity<Kommune> createKommune(@RequestBody Kommune kommune) {
        try {
            Kommune newKommune = repositoryKommuner.save(kommune);
            return new ResponseEntity<>(newKommune, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/kommuner")
    public ResponseEntity<List<Kommune>> getAllKommuner() {
        try {
            List<Kommune> kommuner = repositoryKommuner.findAll();
            if (kommuner.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(kommuner, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/kommune/{id}")
    public ResponseEntity<Kommune> getKommuneById(@PathVariable("id") String id) {
        Optional<Kommune> kommuneData = repositoryKommuner.findById(id);

        if (kommuneData.isPresent()) {
            return new ResponseEntity<>(kommuneData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/kommune/{id}")
    public ResponseEntity<HttpStatus> deleteKommune(@PathVariable("id") String id) {
        try {
            repositoryKommuner.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
