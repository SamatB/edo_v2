package org.example.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Ультилита валидации для проверки типов файлов.
 * Позволяет определить, подходит ли файл под определенные критерии типов файлов,
 * таких как документы Word или изображения.
 */
public class ValidatorFormatFile {

    /**
     * Проверяет, является ли переданный файл документом Word.
     * Допустимыми типами файлов считаются файлы с MIME-типами, указанными в AppConstant.DOC_FILES.
     *
     * @param multipartFile файл для проверки.
     * @return true если файл не пустой и его MIME-тип содержится в списке допустимых MIME-типов документов Word.
     */
    static Boolean isDocFile(MultipartFile multipartFile) {
        return !multipartFile.isEmpty() && AppConstant.DOC_FILES.contains(multipartFile.getContentType());
    }

    /**
     * Проверяет, все ли файлы в списке являются изображениями.
     * Допустимыми типами файлов считаются файлы с MIME-типами, указанными в AppConstant.IMAGES_ONLY.
     *
     * @param multipartFile список файлов для проверки.
     * @return true если список файлов не пустой и все файлы имеют MIME-типы, соответствующие изображениям.
     */
    static Boolean isAllImages(List<MultipartFile> multipartFile) {
        if (multipartFile.isEmpty()) {
            return false;
        }
        for (MultipartFile file : multipartFile) {
            if (!AppConstant.IMAGES_ONLY.contains(file.getContentType()))
                return false;
        }
        return true;
    }


}
