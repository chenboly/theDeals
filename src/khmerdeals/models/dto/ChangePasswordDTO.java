package khmerdeals.models.dto;

public class ChangePasswordDTO {
    private String currentPassword;
    private String newPassword;
    private Integer userId;

    public ChangePasswordDTO() {
    }

    public ChangePasswordDTO(String currentPassword, String newPassword, Integer userId) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.userId = userId;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ChangePasswordDTO{" +
                "currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", userId=" + userId +
                '}';
    }
}
