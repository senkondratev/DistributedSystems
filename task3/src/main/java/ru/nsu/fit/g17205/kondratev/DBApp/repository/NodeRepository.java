package ru.nsu.fit.g17205.kondratev.DBApp.repository;

import ru.nsu.fit.g17205.kondratev.DBApp.model.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<NodeEntity, Long> {
    @Query(value = "SELECT * FROM nodes WHERE earth_distance(ll_to_earth(?1, ?2), ll_to_earth(nodes.latitude, nodes.longitude)) < ?3",
           nativeQuery = true)
    List<NodeEntity> getNodesInRadius(double lat, double lon, double r);
}
