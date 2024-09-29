package com.service.email.mapper;

import com.service.email.dto.response.EmailResponse;
import com.service.email.model.EmailModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmailMapper {

    private final ModelMapper modelMapper;

    public EmailMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EmailResponse asResponse(EmailModel emailModel) {
        return modelMapper.map(emailModel, EmailResponse.class);
    }

    public EmailModel asModel(EmailModel emailModel) {
        return modelMapper.map(emailModel, EmailModel.class);
    }
}
