package sims.sims.Products.ProductServices;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sims.sims.Products.entity.Product;

import java.util.List;

@Service
public interface ProductService {

    List<Product> ViewProducts();

    String AddProduct(Product product);

    String RemoveProduct(Product product);

    String UpdateProduct(Product product);

    String purchaseProduct(Product product);

    ResponseEntity<Object> purchaseMultipleProducts(List<Product> product);
}
