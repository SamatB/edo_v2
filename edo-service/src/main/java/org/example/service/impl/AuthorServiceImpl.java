package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AuthorDto;
import org.example.entity.Author;
import org.example.mapper.AuthorMapper;
import org.example.mapper.FacsimileMapper;
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
        String[] parts = fixLayout(search).split(" ");
        String searchLastName = parts.length > 0 ? parts[0] : "";
        String searchFirstName = parts.length > 1 ? parts[1] : "";
        String searchMiddleName = parts.length > 2 ? parts[2] : "";
        log.info("Начат поиск в БД по фамилии: " + searchLastName + ", имени: " + searchFirstName + ", отчеству: " + searchMiddleName);
        return authorRepository.findByLastNameStartingWithIgnoreCaseAndFirstNameStartingWithIgnoreCaseAndMiddleNameStartingWithIgnoreCase(searchLastName, searchFirstName, searchMiddleName).stream().map(authorMapper::entityToDto).collect(Collectors.toList());
    }

    private static String fixLayout(String input) {
        final String ENGLISH_ALPHABET = "`qwertyuiop[]asdfghjkl;'zxcvbnm,.~QWERTYUIOP{}ASDFGHJKL:\"ZXCVBNM<>";
        final String RUSSIAN_ALPHABET = "ёйцукенгшщзхъфывапролджэячсмитьбюЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ";
        StringBuilder result = new StringBuilder();

        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (ENGLISH_ALPHABET.indexOf(c) != -1) {
                result.append(RUSSIAN_ALPHABET.charAt(ENGLISH_ALPHABET.indexOf(c)));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
