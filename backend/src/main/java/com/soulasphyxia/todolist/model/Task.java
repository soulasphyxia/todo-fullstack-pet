package com.soulasphyxia.todolist.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "task_id")
    @JsonIgnore
    private Long id;

    @JsonIgnore
    @Column(name = "todo_list_id")
    private Long toDoListId;

    @Column(name = "value")
    private String value;

    @Column(name = "completed")
    private boolean completed;



}
