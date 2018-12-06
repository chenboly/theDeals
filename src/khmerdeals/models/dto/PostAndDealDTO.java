package khmerdeals.models.dto;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.sql.Date;
import java.sql.Timestamp;

public class PostAndDealDTO extends RecursiveTreeObject<PostAndDealDTO> {
    private Integer id;
    private String description;
    private double price;
    private String promotion_code;
    private Integer num_of_positive_vote;
    private Integer num_of_negative_vote;
    private Integer maxVotes = 0;
    private Boolean status;
    private Date startDate;
    private Date endDate;
    private UserManagementDTO userManagementDTO;
    private ProductDTO productDTO;
    private StoreDTO storeDTO;
    private ProductImagesDTO productImagesDTO;
    private Timestamp created_date;

    public PostAndDealDTO() {
    }

    public PostAndDealDTO(Integer id, String description, double price, String promotion_code, Integer num_of_positive_vote, Integer num_of_negative_vote, Integer maxVotes, Boolean status, Date startDate, Date endDate, UserManagementDTO userManagementDTO, ProductDTO productDTO, StoreDTO storeDTO, ProductImagesDTO productImagesDTO, Timestamp created_date) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.promotion_code = promotion_code;
        this.num_of_positive_vote = num_of_positive_vote;
        this.num_of_negative_vote = num_of_negative_vote;
        this.maxVotes = maxVotes;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userManagementDTO = userManagementDTO;
        this.productDTO = productDTO;
        this.storeDTO = storeDTO;
        this.productImagesDTO = productImagesDTO;
        this.created_date = created_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPromotion_code() {
        return promotion_code;
    }

    public void setPromotion_code(String promotion_code) {
        this.promotion_code = promotion_code;
    }

    public Integer getNum_of_positive_vote() {
        return num_of_positive_vote;
    }

    public void setNum_of_positive_vote(Integer num_of_positive_vote) {
        this.num_of_positive_vote = num_of_positive_vote;
    }

    public Integer getNum_of_negative_vote() {
        return num_of_negative_vote;
    }

    public void setNum_of_negative_vote(Integer num_of_negative_vote) {
        this.num_of_negative_vote = num_of_negative_vote;
    }

    public Integer getMaxVotes() {
        return maxVotes;
    }

    public void setMaxVotes(Integer maxVotes) {
        this.maxVotes = maxVotes;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public UserManagementDTO getUserManagementDTO() {
        return userManagementDTO;
    }

    public void setUserManagementDTO(UserManagementDTO userManagementDTO) {
        this.userManagementDTO = userManagementDTO;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public StoreDTO getStoreDTO() {
        return storeDTO;
    }

    public void setStoreDTO(StoreDTO storeDTO) {
        this.storeDTO = storeDTO;
    }

    public ProductImagesDTO getProductImagesDTO() {
        return productImagesDTO;
    }

    public void setProductImagesDTO(ProductImagesDTO productImagesDTO) {
        this.productImagesDTO = productImagesDTO;
    }

    public Timestamp getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }

    @Override
    public String toString() {
        return "PostAndDealDTO{" + "id=" + id + ", description='" + description + '\'' + ", price=" + price + ", promotion_code='" + promotion_code + '\'' + ", num_of_positive_vote=" + num_of_positive_vote + ", num_of_negative_vote=" + num_of_negative_vote + ", maxVotes=" + maxVotes + ", status=" + status + ", startDate=" + startDate + ", endDate=" + endDate + ", userManagementDTO=" + userManagementDTO + ", productDTO=" + productDTO + ", storeDTO=" + storeDTO + ", productImagesDTO=" + productImagesDTO + ", created_date=" + created_date + '}';
    }
}
