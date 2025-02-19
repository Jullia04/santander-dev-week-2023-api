package me.dio.domain.service;

import me.dio.domain.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    // O Spring Data JPA já fornece os métodos básicos como save(), findById(), findAll(), delete(), etc.
}
