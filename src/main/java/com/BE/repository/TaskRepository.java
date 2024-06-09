package com.BE.repository;

import com.BE.enums.StatusEnum;
import com.BE.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByStatusEnum(StatusEnum statusEnum);
}
