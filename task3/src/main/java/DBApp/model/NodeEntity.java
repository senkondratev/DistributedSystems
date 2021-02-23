package DBApp.model;

import DBApp.dto.NodeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import DBApp.model.generated.Node;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nodes")
public class NodeEntity {
    @Id
    private long id;

    @Column(name = "username")
    private String user;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "node", orphanRemoval = true,cascade = CascadeType.ALL)
    private List<TagEntity> tags;


    //for nodes without tags inside
    public NodeEntity(long id, String user, Double longitude, Double latitude) {
        this.id = id;
        this.user = user;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    /**
     * Получение нод энтити из сгенерированного класса
     * @param node сгенеренный нод
     * @return нод энтити
     */
    public static NodeEntity convert(Node node) {
        List<TagEntity> tags = node.getTag().stream()
                               .map(tag -> TagEntity.convert(tag, node.getId()))
                               .collect(Collectors.toList());
        return new NodeEntity(node.getId(), node.getUser(), node.getLon(), node.getLat(), tags);
    }

    /**
     * Получение нод энтити из ДТО нода
     * @param node - ДТО нода
     * @return нод энтити
     */
    public static NodeEntity convert(NodeDTO node) {
        List<TagEntity> tags = node.getTags().entrySet().stream()
                                   .map(tag -> TagEntity.convert(tag.getKey(), tag.getValue(), node.getId()))
                                   .collect(Collectors.toList());
        return new NodeEntity(node.getId(), node.getUser(), node.getLongitude(), node.getLatitude(), tags);
    }
}
