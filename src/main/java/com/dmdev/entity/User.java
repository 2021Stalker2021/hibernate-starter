package com.dmdev.entity;

import com.dmdev.type.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "company") // исключаем company
@Builder
@Entity // говорит что эта сущность(User) является Plain old java Object(POJO)
@Table(name = "users", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Column(unique = true)
    private String username;


    @JdbcTypeCode(SqlTypes.JSON)
    private String info;

    @Enumerated(EnumType.STRING) // Сохраняет свойство или поле перечисляемого типа в виде строки в БД.
    private Role role;

    @ManyToOne(optional = false, fetch = FetchType.LAZY) // optional = false - not null констрейнт(наш id не может быть null).
    // FetchType.LAZY - для колекций. FetchType.EAGER - для сущностей. etchType - сразу помогает получать сущности
    @JoinColumn(name = "company_id")
    private Company company;
}
