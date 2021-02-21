package dao;

import model.TagEntity;

import java.sql.SQLException;
import java.util.List;

public interface TagDao {
    List<TagEntity> getTags(long nodeId) throws SQLException;

    void insertTag(TagEntity tag) throws SQLException;

    void insertPreparedTag(TagEntity tag) throws SQLException;

    void batchInsertTags(List<TagEntity> tags) throws SQLException;
}
