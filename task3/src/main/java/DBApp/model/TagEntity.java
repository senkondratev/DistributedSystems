package DBApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import DBApp.model.generated.Node;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tags")
@AllArgsConstructor
@NoArgsConstructor
@IdClass(TagId.class)
public class TagEntity {
    @Id
    private long id;

    @Id
    private String key;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "node_id", insertable = false, updatable = false)
    private NodeEntity node;

    public TagEntity(long nodeId, String k, String v) {
        this.id = nodeId;
        this.key = k;
        this.value = v;
    }

    /**
     * Получение тэг энтити из сгенеренных данных
     * @param tag
     * @param nodeId
     * @return тэг энтити
     */
    public static TagEntity convert(Node.Tag tag, long nodeId) {
        return new TagEntity(nodeId, tag.getK(), tag.getV());
    }

    /**
     * Получение тэг энтити из данных с ДТО
     * @param key
     * @param value
     * @param nodeId
     * @return тэг энтити
     */
    public static TagEntity convert(String key, String value, long nodeId){
        return new TagEntity(nodeId, key, value);
    }
}
