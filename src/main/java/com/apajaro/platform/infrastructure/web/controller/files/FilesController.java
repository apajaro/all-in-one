package com.apajaro.platform.infrastructure.web.controller.files;

import com.apajaro.platform.application.Logger;
import com.apajaro.platform.domain.entity.FileMetadata;
import com.apajaro.platform.domain.exception.DomainException;
import com.apajaro.platform.domain.exception.ErrorCode;
import com.apajaro.platform.domain.service.FileUploadService;
import com.apajaro.platform.domain.valueobject.ID;
import com.apajaro.platform.infrastructure.web.UserPrincipal;
import com.apajaro.platform.infrastructure.web.controller.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
public class FilesController implements FilesResource {
    private final FileUploadService fileUploadService;
    private final Logger logger;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<String> postFile(MultipartFile file, UserPrincipal userPrincipal) {
        try {
            InputStream inputStream = file.getInputStream();

            FileMetadata fileMetadata = FileMetadata.builder()
                    .id(ID.generate())
                    .size(file.getSize())
                    .contentType(file.getContentType())
                    .build();

            String fileUrl = fileUploadService.uploadFile(inputStream, fileMetadata, userPrincipal.getOrganizationId());

            return new ApiResponse<>(true, fileUrl);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new DomainException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
