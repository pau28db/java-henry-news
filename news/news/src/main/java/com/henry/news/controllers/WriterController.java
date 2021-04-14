package com.henry.news.controllers;

import com.henry.news.converter.WriterToWriterDTOConverter;
import com.henry.news.models.DTO.WriterDTO;
import com.henry.news.models.Writer;
import com.henry.news.services.WriterService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/writer")
public class WriterController {

    @Autowired
    private WriterService writerService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private WriterToWriterDTOConverter writerToWriterDTOConverter;

    @GetMapping
    @Operation(summary = "Get all writers")
    public List<Writer> getWriters(){
        return writerService.getWriters();
    }

    @GetMapping("/writerDTO/{id}")
    @Operation(summary = "filtered writers (no dni)")
    public WriterDTO getWriterDTO(@PathVariable Integer id){
        return conversionService.convert(writerService.getWriter(id), WriterDTO.class);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get a writer by id")
    public Writer getWriter(@PathVariable Integer id) {
        return writerService.getWriter(id);
    }

    @PostMapping
    @Operation(summary = "Add a writer")
    public String addWriter(@RequestBody Writer writer) {
        Writer postwriter = writerService.addWriter(writer);
        return ("Writer added: " + postwriter);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a writer by id")
    public String deleteWrite(@PathVariable Integer id){
        writerService.deleteWriterByid(id);
        return ("Writer deleted with id: " + id);
    }

    @PutMapping
    @Operation(summary = "Modify a writer ")
    public String editWriter(@RequestBody Writer writer){
        Writer putwriter = writerService.editWriter(writer);
        return ("Writer modified " + putwriter);
    }
}
