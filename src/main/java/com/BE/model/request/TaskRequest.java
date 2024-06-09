package com.BE.model.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TaskRequest {
    String date;
    String title;
    String description;
    String deadline;
}
