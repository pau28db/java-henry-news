package com.henry.news.converter;

import com.henry.news.models.DTO.WriterDTO;
import com.henry.news.models.Writer;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WriterToWriterDTOConverter implements Converter<Writer, WriterDTO> {

    private final ModelMapper modelMapper;

    public WriterToWriterDTOConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public  WriterDTO convert(Writer source) {
        return modelMapper.map(source, WriterDTO.class);
    }
}
