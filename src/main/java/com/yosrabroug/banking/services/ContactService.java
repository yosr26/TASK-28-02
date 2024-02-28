package com.yosrabroug.banking.services;

import com.yosrabroug.banking.dto.ContactDto;

import java.util.List;

public interface ContactService extends AbstractServices<ContactDto>{

    List<ContactDto> findAllByUserId(Integer userId);
}
