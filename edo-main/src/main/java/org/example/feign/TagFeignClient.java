package org.example.feign;

import org.example.dto.TagDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "edo-service")
public interface TagFeignClient {

    /**
     * Метод для сохранения метки
     */
    @PostMapping("/tag")
    TagDto saveTag(@RequestBody TagDto tagDto);

}
