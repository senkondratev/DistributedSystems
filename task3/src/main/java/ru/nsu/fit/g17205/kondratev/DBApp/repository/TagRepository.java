package ru.nsu.fit.g17205.kondratev.DBApp.repository;

import ru.nsu.fit.g17205.kondratev.DBApp.model.TagEntity;
import ru.nsu.fit.g17205.kondratev.DBApp.model.TagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, TagId>{
}
