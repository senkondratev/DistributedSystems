package DBApp.repository;

import DBApp.model.TagEntity;
import DBApp.model.TagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, TagId>{
}
