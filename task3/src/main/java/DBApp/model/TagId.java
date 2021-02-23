package DBApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TagId implements Serializable {

    @Column(name = "node_id")
    private long id;

    @Column(name = "key")
    private String key;
}
