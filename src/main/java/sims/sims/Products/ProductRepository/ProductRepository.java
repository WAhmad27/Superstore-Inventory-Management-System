package sims.sims.Products.ProductRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sims.sims.Products.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from products where Product_ID  = ?1", nativeQuery = true)
    Optional<Product> findProductByProduct_ID(String Product_ID);

    @Query(value = "select * from products where Product_Name  = ?1", nativeQuery = true)
    Optional<Product> findProductByProduct_Name(String Product_Name);

    @Query(value = "select * from products where Product_Name = ?1", nativeQuery = true)
    Product findProductWhereProductNameIs(String Product_Name);

    @Modifying(clearAutomatically = true)
    @Query(value = "update products set Quantity = ?1 where Product_Name = ?2", nativeQuery = true)
    int updateProductsByProductName(Integer quantity, String Product_Name);
}