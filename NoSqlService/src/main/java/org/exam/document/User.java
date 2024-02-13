package org.exam.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //    we can not auto generate in mongodb as in sql databases
    //    we need to config that by the given below method
    //    We also annotate it with the @Transient to prevent it from being persisted alongside other properties of the model.

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private Integer id;

    private String name;
}
