package sims.sims.Products.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
@Table(name = "Products")
public class Product {
    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    private Integer ID;

    @Column(name = "Product_ID")
    private String productId;

    @Column(name = "Product_Name")
    private String productName;

    @Column(name = "Price")
    private Integer price;

    @Column(name = "Quantity")
    private Integer quantity;
}

