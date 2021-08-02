package br.com.zupacademy.eduardo.mercadolivre.component;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Primary
public class UploaderFake implements Uploader {
    public Set<String> upload(List<MultipartFile> files) {
        return files.stream().map(img -> "https://s3.amazon.com/" + img.getName() + UUID.randomUUID().toString())
                .collect(Collectors.toSet());
    }
}
