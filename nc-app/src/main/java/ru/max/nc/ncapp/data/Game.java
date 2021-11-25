package ru.max.nc.ncapp.data;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
@Builder(toBuilder = true)
@Table(name = "game")
public class Game  {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;

    private String createdBy;

    private int fieldSize;
}
