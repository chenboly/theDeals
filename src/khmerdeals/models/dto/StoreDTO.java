package khmerdeals.models.dto;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.sql.Date;

public class StoreDTO extends RecursiveTreeObject<StoreDTO> {
    private Integer id;
    private UserManagementDTO userManagementDTO;
    private String name;
    private String address;
    private String phone;
    private String image_url;
    private String website;
    private Boolean status;
    private Date created_date;

    public StoreDTO() {
    }

    public StoreDTO(Integer id, UserManagementDTO userManagementDTO, String name, String address, String phone, String image_url, String website, Boolean status, Date created_date) {
        this.id = id;
        this.userManagementDTO = userManagementDTO;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.image_url = image_url;
        this.website = website;
        this.status = status;
        this.created_date = created_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserManagementDTO getUserManagementDTO() {
        return userManagementDTO;
    }

    public void setUserManagementDTO(UserManagementDTO userManagementDTO) {
        this.userManagementDTO = userManagementDTO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    @Override
    public String toString() {
        return "StoreDTO{" +
                "id=" + id +
                ", userManagementDTO=" + userManagementDTO +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", image_url='" + image_url + '\'' +
                ", website='" + website + '\'' +
                ", status=" + status +
                ", created_date=" + created_date +
                '}';
    }


//    @Override
//    public String toString() {
//        return name;
//    }
}
