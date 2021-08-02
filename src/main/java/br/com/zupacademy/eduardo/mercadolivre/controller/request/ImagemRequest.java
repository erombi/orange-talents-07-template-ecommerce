package br.com.zupacademy.eduardo.mercadolivre.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ImagemRequest {

    @NotNull
    @Size(min = 1)
    private List<MultipartFile> files = new ArrayList<>();

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ImagemRequest(List<MultipartFile> files) {
        this.files = files;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }
}
