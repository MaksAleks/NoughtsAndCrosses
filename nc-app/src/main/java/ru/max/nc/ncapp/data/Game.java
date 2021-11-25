package ru.max.nc.ncapp.data;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import ru.max.nc.ncapp.api.dto.GameDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

import static ru.max.nc.ncapp.api.dto.GameDto.*;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@With
@Builder(toBuilder = true)
@Table(name = "game")
public class Game  {

    @Id
    @NotNull
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "name", updatable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "created_by", updatable = false, unique = true)
    private String createdBy;

    private String secondPlayer;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Column(name = "field_size", updatable = false)
    private int fieldSize;
}
