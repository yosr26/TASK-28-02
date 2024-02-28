package com.yosrabroug.banking.services.impl;

import com.yosrabroug.banking.dto.AdresseDto;
import com.yosrabroug.banking.models.Adresse;
import com.yosrabroug.banking.repositories.AdresseRepository;
import com.yosrabroug.banking.services.AdresseService;
import com.yosrabroug.banking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdresseServiceImpl implements AdresseService {

    private final AdresseRepository repository;
    private final ObjectsValidator validator;
    @Override
    public Integer save(AdresseDto dto) {
        validator.validate(dto);
        Adresse adresse = AdresseDto.toEntity(dto);
        Adresse savedAdresse = repository.save(adresse);
        return savedAdresse.getId();
    }

    @Override
    public List<AdresseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(AdresseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AdresseDto findById(Integer id) {
        return repository.findById(id
                )
                .map(AdresseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune adresse avec cet id existe"+id));
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);

    }
}
