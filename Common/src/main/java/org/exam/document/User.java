package org.exam.document;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private Boolean active;
    @Column(columnDefinition = "TEXT")
    private String about;
}
