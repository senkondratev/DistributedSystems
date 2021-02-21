package dao;

import db.DBConnection;
import model.TagEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagDaoImpl implements TagDao{
    private static void prepareStatement(PreparedStatement statement, TagEntity tag) throws SQLException {
        statement.setLong(1, tag.getNodeId());
        statement.setString(2, tag.getKey());
        statement.setString(3, tag.getValue());
    }

    private static TagEntity mapTag(ResultSet rs) throws SQLException {
        return new TagEntity(rs.getLong("node_id"), rs.getString("key"),
                rs.getString("value"));
    }

    @Override
    public List<TagEntity> getTags(long nodeId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_GET);
        statement.setLong(1, nodeId);
        ResultSet resultSet = statement.executeQuery(SqlQueries.SQL_GET);
        List<TagEntity> tags = new ArrayList<>();
        while (resultSet.next()) {
            tags.add(mapTag(resultSet));
        }
        return tags;
    }

    @Override
    public void insertTag(TagEntity tag) throws SQLException {
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        String sql = "insert into tags(node_id, key, value) " +
                "values (" + tag.getNodeId() + ", '" + tag.getKey() +
                "', '" + tag.getValue().replaceAll("'", "") + "')";
        statement.execute(sql);
    }

    @Override
    public void insertPreparedTag(TagEntity tag) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(TagDaoImpl.SqlQueries.SQL_INSERT);
        prepareStatement(statement, tag);
        statement.execute();
    }

    @Override
    public void batchInsertTags(List<TagEntity> tags) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(TagDaoImpl.SqlQueries.SQL_INSERT);
        for (TagEntity tag : tags) {
            prepareStatement(statement, tag);
            statement.addBatch();
        }
        statement.executeBatch();
    }

    private static class SqlQueries {
        private static final String SQL_GET = "" +
                "select node_id, key, value " +
                "from tags " +
                "where node_id = ?";

        private static final String SQL_INSERT = "" +
                "insert into tags(node_id, key, value) " +
                "values (?, ?, ?)";

    }
}
