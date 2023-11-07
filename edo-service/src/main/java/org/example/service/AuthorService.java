package org.example.service;

import org.example.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAuthorsByFirstLetters(String search);
}
