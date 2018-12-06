package khmerdeals.models.dto;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.sql.Date;
import java.util.List;

public class ProductDTO extends RecursiveTreeObject<ProductDTO> {
    private Integer id;
    private String name;
    private StoreDTO storeDTO;
    private Boolean status;
    private Date created_date;
    private List<ProductImagesDTO> productImgagesDTO;

    public ProductDTO() {
    }

    public ProductDTO(Integer id, String name, StoreDTO storeDTO, Boolean status, Date created_date, List<ProductImagesDTO> productImgagesDTO) {
        this.id = id;
        this.name = name;
        this.storeDTO = storeDTO;
        this.status = status;
        this.created_date = created_date;
        this.productImgagesDTO = productImgagesDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StoreDTO getStoreDTO() {
        return storeDTO;
    }

    public void setStoreDTO(StoreDTO storeDTO) {
        this.storeDTO = storeDTO;
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

    public List<ProductImagesDTO> getProductImgagesDTO() {
        return productImgagesDTO;
    }

    public void setProductImgagesDTO(List<ProductImagesDTO> productImgagesDTO) {
        this.productImgagesDTO = productImgagesDTO;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", storeDTO=" + storeDTO +
                ", status=" + status +
                ", created_date=" + created_date +
                ", productImgagesDTO=" + productImgagesDTO +
                '}';
    }
}
