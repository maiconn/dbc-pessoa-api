package com.dbc.pessoaapi.controller;

import com.dbc.pessoaapi.entity.ProfessorEntity;
import com.dbc.pessoaapi.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorRepository repository;

    @GetMapping
    public List<ProfessorEntity> list() {
        return repository.findAll();
    }

    @PostMapping
    public ProfessorEntity create(@RequestBody ProfessorEntity professor) {
        return repository.save(professor);
    }
}
