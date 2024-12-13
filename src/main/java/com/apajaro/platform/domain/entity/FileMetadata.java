package com.apajaro.platform.domain.entity;

import com.apajaro.platform.domain.valueobject.ID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileMetadata {
    ID id;
    Long size;
    String contentType;
}
