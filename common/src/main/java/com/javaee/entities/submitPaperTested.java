package com.javaee.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class submitPaperTested {
    Integer paperId;
    Integer paperQuestionTestedId;
    Integer score;
    Integer questionId;
}
