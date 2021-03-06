package ru.max.nc.ncapp.data;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@With
@Builder(toBuilder = true)
@Table(name = "move")
@EntityListeners(AuditingEntityListener.class)
public class Move implements Serializable {

    @Id
    @NotNull
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "x_pos", updatable = false)
    private int posX;

    @NotNull
    @Column(name = "y_pos", updatable = false)
    private int posY;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private Instant createdTime;

    @NotNull
    @Column(name = "user_name", updatable = false)
    private String userName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "game_name",
            referencedColumnName = "name"
    )
    private Game game;
}
