package db;

import dao.NodeDao;
import dao.TagDao;
import lombok.AllArgsConstructor;
import model.NodeEntity;
import model.TagEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class NodeProcessor {
    private static final int BATCH_SIZE = 5000;

    private final NodeDao nodeDao;
    private final TagDao tagDao;
    private final List<NodeEntity> nodeBuffer = new ArrayList<>();

    public void putNodePrepared(NodeEntity node) {
        try {
            nodeDao.insertPreparedNode(node);
            for (TagEntity tag : node.getTags()) {
                tagDao.insertPreparedTag(tag);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void putNode(NodeEntity node) {
        try {
            nodeDao.insertNode(node);
            for (TagEntity tag : node.getTags()) {
                tagDao.insertTag(tag);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void putNodeBuffered(NodeEntity node) {
        nodeBuffer.add(node);
        if (nodeBuffer.size() == BATCH_SIZE) {
            flush();
        }
    }

    public void flush() {
        try {
            nodeDao.batchInsertNodes(nodeBuffer);
            List<TagEntity> tags = nodeBuffer.stream()
                                         .flatMap(node -> node.getTags().stream())
                                         .collect(Collectors.toList());
            tagDao.batchInsertTags(tags);
            nodeBuffer.clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public NodeEntity getNode(long id) {
        try {
            return nodeDao.getNode(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
