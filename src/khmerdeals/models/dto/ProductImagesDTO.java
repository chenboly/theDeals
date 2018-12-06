package khmerdeals.models.dto;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.sql.Date;

public class ProductImagesDTO extends RecursiveTreeObject<ProductImagesDTO> {
    private Integer id;
    private ProductDTO productDTO;
    private String image_url;
    private String imageFileName;
    private Boolean status;
    private Date created_date;

    public ProductImagesDTO() {
    }

    public ProductImagesDTO(Integer id, ProductDTO productDTO, String image_url, String imageFileName, Boolean status, Date created_date) {
        this.id = id;
        this.productDTO = productDTO;
        this.image_url = image_url;
        this.imageFileName = imageFileName;
        this.status = status;
        this.created_date = created_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
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
        return "ProductImagesDTO{" +"id=" + id + ", " +
                "productDTO=" + productDTO + ", " +
                "image_url='" + image_url + '\'' + ", " +
                "imageFileName='" + imageFileName + '\'' + ", " +
                "status=" + status + ", created_date=" + created_date + '}';
    }
}
