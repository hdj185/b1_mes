package com.B1team.b01.config;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomModelMapper {

    private static ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();

        // LocalDateTime을 String으로 매핑하는 컨버터 등록
        Converter<LocalDateTime, String> localDateTimeToStringConverter = new Converter<>() {
            @Override
            public String convert(MappingContext<LocalDateTime, String> context) {
                LocalDateTime source = context.getSource();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                return source.format(formatter);
            }
        };

        // LocalDateTime -> String 매핑 설정
        modelMapper.createTypeMap(LocalDateTime.class, String.class);
        modelMapper.addConverter(localDateTimeToStringConverter);
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }
}
