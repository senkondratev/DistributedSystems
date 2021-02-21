package dao;

import db.DBConnection;
import model.NodeEntity;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class NodeDaoImpl implements NodeDao {

    @Override
    public NodeEntity getNode(long nodeId) throws SQLException {
        return null;
    }

    @Override
    public void insertNode(NodeEntity node) throws SQLException {
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        String sql = "insert into nodes(id, username, longitude, latitude) " +
                "values (" + node.getId() + ", '" + node.getUser() + "', " + node.getLongitude() +
                ", " + node.getLatitude() + ")";
        statement.execute(sql);
    }

    @Override
    public void insertPreparedNode(NodeEntity node) throws SQLException {

    }

    @Override
    public void batchInsertNodes(List<NodeEntity> nodes) throws SQLException {

    }
}
