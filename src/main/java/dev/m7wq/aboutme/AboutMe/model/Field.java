package dev.m7wq.aboutme.AboutMe.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Field {

    String head;
    String description;
}
