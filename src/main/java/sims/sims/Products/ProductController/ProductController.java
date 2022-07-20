package sims.sims.Products.ProductController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sims.sims.Products.ProductServices.ProductService;
import sims.sims.Products.entity.Product;

import java.util.List;

@RestController
@RequestMapping(path = "sims/")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(path = "view")
    public List<Product> View() {
        return productService.ViewProducts();
    }

    @PostMapping(path = "add")
    public String AddProduct(@RequestBody Product product) {
        String response;
        try {
            response = productService.AddProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
        }
        return response;
    }

    @PostMapping(path = "remove")
    public String RemoveProduct(@RequestBody Product product) {
        String response;
        try {
            response = productService.RemoveProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
        }
        return response;
    }

    @PostMapping(path = "update")
    public String UpdateProduct(@RequestBody Product product) {
        String response;
        try {
            response = productService.UpdateProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
        }
        return response;
    }

    @PostMapping(path = "purchaseItem")
    public String purchaseProduct(@RequestBody Product product) {
        String response;
        try {
            response = productService.purchaseProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
        }
        return response;
    }

    @PostMapping(path = "purchaseItems")
    public String purchaseProducts(@RequestBody List<Product> product) {
        String response;
        try {
            response = String.valueOf(productService.purchaseMultipleProducts(product));
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
        }
        return response;
    }
}
