package com.dmdev.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.annotations.Cache;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(exclude = "users")
@Builder
@Entity
//@BatchSize(size = 3)
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Company")
public class Company implements BaseEntity<Integer> {
    // Можно не использовать аннотацию table т.к. sql не чуствителен к регистру (не важно что у нас класс начинается с большой буквы)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "username")
    @SortNatural
    @NotAudited
    private Map<String, User> users = new TreeMap<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "company_locale")
//    @AttributeOverride(name = "lang", @Column(name = "lang"))
    @Column(name = "description")
    @MapKeyColumn(name = "lang")
    @NotAudited
    private Map<String, String> locales = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getUsername(), user);
        user.setCompany(this);
    }
}


