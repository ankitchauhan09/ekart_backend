    package com.example.ekart_backend.entities;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

    import java.util.*;
    import java.util.stream.Collectors;

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class User implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        private Integer id;
        @Column(name = "user-name")
        private String userRealName;
        @Column(unique = true)
        private String userEmail;
        private String userPassword;
        private String userContact;
        private String userRecoveryEmail;
        @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
        private List<Address> address = new ArrayList<>();

        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user", referencedColumnName = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role",
                referencedColumnName = "role_id")
        )
        private Set<Role> role = new HashSet<>();



        @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
        private List<Cart> cart = new ArrayList<>();

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.role.stream().map((role1) -> new SimpleGrantedAuthority(role1.getRoleName())).collect(Collectors.toList());
        }

        @Override
        public String getPassword() {
            return this.userPassword;
        }

        @Override
        public String getUsername() {
            return userEmail;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
