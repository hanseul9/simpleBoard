package hanseul.simpleBoard.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @JoinColumn(name = "member_id")
    private Long id;

    private String email;
    private String name;
    private String password;


    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();


    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
