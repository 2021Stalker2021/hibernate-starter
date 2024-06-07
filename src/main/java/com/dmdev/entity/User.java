package com.dmdev.entity;

import jakarta.persistence.*;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.dmdev.util.StringUtils.SPACE;

@NamedEntityGraph(
        name = "WithCompany",
        attributeNodes = {
                @NamedAttributeNode("company")
        }
)
@NamedEntityGraph(
        name = "WithCompanyAndChat",
        attributeNodes = {
                @NamedAttributeNode("company"),
                @NamedAttributeNode(value = "userChats", subgraph = "chats")
        },
        subgraphs = {
                @NamedSubgraph(name = "chats", attributeNodes = @NamedAttributeNode("chat"))
        }
)
@FetchProfile(name = "withCompanyAndPayment", fetchOverrides = {
        @FetchProfile.FetchOverride(
                entity = User.class, association = "company", mode = FetchMode.JOIN
        ),
        @FetchProfile.FetchOverride(
                entity = User.class, association = "payments", mode = FetchMode.JOIN
        ),
})
@NamedQuery(name = "findUserByName", query = "select u from User u " +
        "left join u.company c " +
        "where u.personalInfo.firstname = :firstname and c.name = :companyName " +
        "order by u.personalInfo.lastname desc ")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "username")
@ToString(exclude = {"company", "userChats", "payments"})
@Builder
@Entity // говорит что эта сущность(User) является Plain old java Object(POJO)
@Table(name = "users", schema = "public")
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "User")
public  class User implements Comparable<User>, BaseEntity<Long> {

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

    @ManyToOne(fetch = FetchType.LAZY) // optional = false - not null констрейнт(наш id не может быть null).
    // FetchType.LAZY - для колекций. FetchType.EAGER - для сущностей. etchType - сразу помогает получать сущности
    // cascade = CascadeType.DETACH - после удаления из кэша нашего юзера удаляется из кэша и его компания
//    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "company_id")
    private Company company;

//    @OneToOne(mappedBy = "user",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY)
////            optional = false) // optional = false говорит об том что сущность profile обязана быть в DB в users
//    private Profile profile;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @NotAudited
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<UserChat> userChats = new ArrayList<>();

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }

    @Builder.Default
//    @Fetch(FetchMode.SUBSELECT)

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    @NotAudited
    private List<Payment> payments = new ArrayList<>();

    public String fullName() {
        return getPersonalInfo().getFirstname() + SPACE + getPersonalInfo().getLastname();
    }
}
