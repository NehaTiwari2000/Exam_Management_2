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
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean active;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Double maxMarks;
    private Integer numberOfQuestions;


    @ManyToOne
    private Cateory cateory;
}
