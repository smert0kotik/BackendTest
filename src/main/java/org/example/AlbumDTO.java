package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumDTO {
    private String id;
    private String image_id;
    private String comment;
    private String author;
    private String title;
}
