package khmerdeals.models.dto;


import java.sql.Date;
import java.util.List;

public class UserManagementDTO {
    private Integer id;
    private String fullName;
    private String userName;
    private String passWord;
    private String image_url;
    private String phone;
    private String email;
    private Boolean status;
    private Date created_date;
    private List<UserRoleDTO> userRoleDTOList;
    private List<StoreDTO> storeDTOList;

    public UserManagementDTO() {
    }

    public UserManagementDTO(Integer id, String fullName, String userName, String passWord, String image_url, String phone, String email, Boolean status, Date created_date, List<UserRoleDTO> userRoleDTOList, List<StoreDTO> storeDTOList) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.passWord = passWord;
        this.image_url = image_url;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.created_date = created_date;
        this.userRoleDTOList = userRoleDTOList;
        this.storeDTOList = storeDTOList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public List<UserRoleDTO> getUserRoleDTOList() {
        return userRoleDTOList;
    }

    public void setUserRoleDTOList(List<UserRoleDTO> userRoleDTOList) {
        this.userRoleDTOList = userRoleDTOList;
    }

    public List<StoreDTO> getStoreDTOList() {
        return storeDTOList;
    }

    public void setStoreDTOList(List<StoreDTO> storeDTOList) {
        this.storeDTOList = storeDTOList;
    }

    @Override
    public String toString() {
        return "UserManagementDTO{" + "id=" + id + ", fullName='" + fullName + '\'' + ", userName='" + userName + '\'' + ", passWord='" + passWord + '\'' + ", image_url='" + image_url + '\'' + ", phone='" + phone + '\'' + ", email='" + email + '\'' + ", status=" + status + ", created_date=" + created_date + ", userRoleDTOList=" + userRoleDTOList + ", storeDTOList=" + storeDTOList + '}';
    }
}
