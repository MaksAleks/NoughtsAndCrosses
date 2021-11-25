package ru.max.nc.ncapp.data;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

import static ru.max.nc.ncapp.api.dto.GameDto.Status;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@With
@Builder(toBuilder = true)
@Table(name = "game")
public class Game implements Serializable {

    @Id
    @NotNull
    @GeneratedValue
    private UUID id;

    @NotNull
    @NaturalId
    @Column(name = "name", updatable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "created_by", updatable = false, unique = true)
    private String createdBy;

    private String secondPlayer;

    @NotNull
    @Setter
    private String nextMove;

    private long leftMovesCount;

    @Version
    private long version;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Column(name = "field_size", updatable = false)
    private int fieldSize;

    @PrePersist
    public void prePersist() {
        nextMove = createdBy;
        leftMovesCount = (long) fieldSize * fieldSize;
    }

    public Game decrementLeftMovesCount() {
        if (Status.FINISHED.equals(status)) {
            throw new IllegalArgumentException("Game is already finished");
        }
        if (--leftMovesCount <= 0) {
            status = Status.FINISHED;
        }
        return this;
    }

    public boolean isFinished() {
        return status.equals(Status.FINISHED);
    }

    public boolean isNotFinished() {
        return !isFinished();
    }
}
