package com.dmdev.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable // говорит что это встраевамый компонент
public class PersonalInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String firstname;
    private String lastname;

    private Birthday birthDate;

}
