package dao;

import model.NodeEntity;

import java.sql.SQLException;
import java.util.List;

public interface NodeDao {
    NodeEntity getNode(long nodeId) throws SQLException;

    void insertNode(NodeEntity node) throws SQLException;

    void insertPreparedNode(NodeEntity node) throws SQLException;

    void batchInsertNodes(List<NodeEntity> nodes) throws SQLException;
}
