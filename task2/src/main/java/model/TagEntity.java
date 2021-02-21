package model;

import lombok.Data;
import model.generated.Node;

@Data
public class TagEntity {
    private final long nodeId;
    private final String key;
    private final String value;

    public static TagEntity convert(Node.Tag tag, long nodeId) {
        return new TagEntity(nodeId, tag.getK(), tag.getV());
    }
}
