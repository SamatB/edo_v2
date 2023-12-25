package org.example.utils;

public class CheckingLayout {

    /**
     * Проверка расскладки клавиатуры(проверяет и при необходимости конвертирует строку в русские символы).
     *
     * @param input строка символов в русской или английской расскладке
     * @return возвращает строку в русской расскладке
     */

    public static String fixLayout(String input) {
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
