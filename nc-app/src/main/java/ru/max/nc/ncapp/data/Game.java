package ru.max.nc.ncapp.data;

import lombok.*;
import ru.max.nc.ncapp.api.dto.GameDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Column(name = "created_by", unique = true)
    private String createdBy;

    private String secondPlayer;

    private Status status;

    private int fieldSize;
}
