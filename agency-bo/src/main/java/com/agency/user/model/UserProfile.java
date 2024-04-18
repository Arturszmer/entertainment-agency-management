package com.agency.user.model;

import com.agency.auth.RoleType;
import com.agency.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "user_profile")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile extends BaseEntity<Long> implements UserDetails {

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column(name = "roles")
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_profile_roles",
                joinColumns = @JoinColumn(name = "user_profile_id", referencedColumnName = "ID"),
                inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "ID"))
    private List<Role> roles = new ArrayList<>();

    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    private UserProfile(String username,  String email, String password, RoleType roleType) {
        this.username = username;
        this.password = password;
        this.email = email;
        addRole(new Role(roleType));
    }

    public static UserProfile create(String username,
                                     String email,
                                     String password,
                                     RoleType roleType){
        return new UserProfile(username, email, password, roleType);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> Stream.concat(
                        Stream.of(new SimpleGrantedAuthority(
                                "ROLE_" + role.getName())),
                        role.getPermissions().stream()
                                .map(permission ->
                                        new SimpleGrantedAuthority(permission.name())))
                )
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addRole(Role role){
       this.roles.add(role);
    }

    public void setNewPassword(String newPassword) {
        password = newPassword;
    }

    public void lockUserAccount() {
        if(isAccountNonLocked()){
            this.accountNonLocked = false;
        } else {
            throw new IllegalStateException("User is already blocked");
        }
    }

    public void unblockUserAccount() {
        if(!isAccountNonLocked()){
            this.accountNonLocked = true;
        } else {
            throw new IllegalStateException("User is not blocked");
        }
    }
}
