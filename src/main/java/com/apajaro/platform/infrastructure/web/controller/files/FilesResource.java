package com.apajaro.platform.infrastructure.web.controller.files;

import com.apajaro.platform.infrastructure.web.CurrentUser;
import com.apajaro.platform.infrastructure.web.UserPrincipal;
import com.apajaro.platform.infrastructure.web.controller.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/files")
public interface FilesResource {
    @PostMapping
    ApiResponse<String> postFile(@RequestParam(value = "file") MultipartFile file, @CurrentUser UserPrincipal userPrincipal);
}
