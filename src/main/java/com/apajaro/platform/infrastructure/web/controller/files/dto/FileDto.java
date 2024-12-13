package com.apajaro.platform.infrastructure.web.controller.files.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class FileDto {
    @NotNull
    MultipartFile file;
}
