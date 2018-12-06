package khmerdeals.models.dto;

public class UserRoleDTO {
    private Integer id;
    private String role_name;

    public UserRoleDTO() {
    }
    public UserRoleDTO(Integer id, String role_name) {
        this.id = id;
        this.role_name = role_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {

        this.role_name = role_name;
    }

    @Override
    public String toString() {
        return "UserRoleDTO{" +
                "id=" + id +
                ", role_name='" + role_name + '\'' +
                '}';
    }
}
