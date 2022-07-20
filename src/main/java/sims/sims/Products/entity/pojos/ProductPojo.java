package sims.sims.Products.entity.pojos;


import lombok.Data;

@Data
public class ProductPojo {

    private String productName;

    private Integer price;

    private Integer quantity;

    private String message;

    private Integer billedAmount;


}
