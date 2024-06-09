package com.BE.model.entity;


import com.BE.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String dateCreate;
    String title;
    String description;
    String deadline;
    @Enumerated(EnumType.STRING)
    StatusEnum statusEnum;
}
