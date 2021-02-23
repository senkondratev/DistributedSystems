package DBApp.db;

import DBApp.repository.NodeRepository;
import DBApp.repository.TagRepository;
import lombok.AllArgsConstructor;
import DBApp.model.NodeEntity;
import DBApp.model.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NodeProcessor {
    @Autowired
    private final NodeRepository nodeRepository;

    @Autowired
    private final TagRepository tagRepository;

    public NodeEntity save(NodeEntity node) {
        NodeEntity nodeEntity = nodeRepository.save(node);
        //tagRepository.saveAll(node.getTags()); - не нужно благодаря cascade = CascadeType.ALL в NodeEntity
        return nodeEntity;
    }

    public NodeEntity getNode(Long id) {
        return nodeRepository.findById(id).get();
    }

    public NodeEntity update(Long id, NodeEntity node) {
        NodeEntity nodeFromDb = nodeRepository.findById(id).orElseThrow(NullPointerException::new);
        node.setId(nodeFromDb.getId());
        return nodeRepository.save(node);
    }

    public void delete(long id) {
        NodeEntity node = nodeRepository.findById(id).orElseThrow(NullPointerException::new);
        nodeRepository.delete(node);
    }

    public List<NodeEntity> findNodesInRadius(Double latitude, Double longitude, Double radius) {
        return nodeRepository.getNodesInRadius(latitude, longitude, radius);
    }
}
