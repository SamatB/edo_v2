package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AuthorDto;
import org.example.mapper.AuthorMapper;
import org.example.repository.AuthorRepository;
import org.example.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorDto> getAuthorsByFirstLetters(String search) {
        String fixedSearch = fixLayout(search);
        log.info("Начат поиск в БД по ФИО: " + fixedSearch);
        return authorRepository.searchByFullName(fixedSearch)
                .stream()
                .map(authorMapper::entityToDto)
                .collect(Collectors.toList());
    }

    private static String fixLayout(String input) {
        final String ENGLISH_ALPHABET = "`qwertyuiop[]asdfghjkl;'zxcvbnm,.~QWERTYUIOP{}ASDFGHJKL:\"ZXCVBNM<>";
        final String RUSSIAN_ALPHABET = "ёйцукенгшщзхъфывапролджэячсмитьбюЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ";
        StringBuilder result = new StringBuilder();

        char[] chars = input.toCharArray();
        for (char c : chars) {
            if (ENGLISH_ALPHABET.indexOf(c) != -1) {
                result.append(RUSSIAN_ALPHABET.charAt(ENGLISH_ALPHABET.indexOf(c)));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
