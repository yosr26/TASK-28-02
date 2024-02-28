package com.yosrabroug.banking.controllers;

import com.yosrabroug.banking.dto.AdresseDto;
import com.yosrabroug.banking.services.AdresseService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adresses")
@RequiredArgsConstructor
public class AdresseController {

    private final AdresseService service;

    @PostMapping("/")
    public ResponseEntity<Integer> save(
            @RequestBody AdresseDto adresseDto
            ){
        return ResponseEntity.ok(service.save(adresseDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<AdresseDto>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{adresse-id}")
    public ResponseEntity<AdresseDto> findById(
            @PathVariable("adresse-id") Integer adresseId
    ){
        return ResponseEntity.ok(service.findById(adresseId));
    }

    @DeleteMapping("/{adresse-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("adresse-id") Integer adresseId
    ){
        return ResponseEntity.accepted().build();
    }



}
