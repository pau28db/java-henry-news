package com.henry.news.services;

import com.henry.news.models.Writer;
import com.henry.news.repositories.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class WriterService {
    @Autowired
    private WriterRepository writerRepository;

    public List<Writer> getWriters() {
        return writerRepository.findAll();
    }

    public Writer addWriter(Writer writer) {
        return writerRepository.save(writer);
    }

    public Writer getWriter(Integer id) {
        return writerRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteWriterByid(Integer id) {
        writerRepository.deleteById(id);
    }

    public Writer editWriter(Writer write) {
        Writer writer = writerRepository.findById(write.getId()).orElse(new Writer());
        Writer editwriter = new Writer();
        editwriter.setId(writer.getId());

        if (write.getName() != null) {
            editwriter.setName(write.getName());
        } else {
            editwriter.setName(writer.getName());
        }
        if (write.getLastName() != null) {
            editwriter.setLastName(write.getLastName());
        } else {
            editwriter.setLastName(writer.getLastName());
        }
        if (write.getDni() != null) {
            editwriter.setDni(write.getDni());
        } else {
            editwriter.setDni(writer.getDni());
        }
        return writerRepository.save(editwriter);
    }


}
