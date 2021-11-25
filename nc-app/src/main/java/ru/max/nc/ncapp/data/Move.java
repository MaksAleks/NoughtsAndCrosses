package ru.max.nc.ncapp.data;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class Move {

    @Id
    @NotNull
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "x_pos", updatable = false)
    private int x;

    @NotNull
    @Column(name = "y_pos", updatable = false)
    private int y;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private Instant createdTime;
}
