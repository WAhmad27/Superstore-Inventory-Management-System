package sims.sims.Products.ProductServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sims.sims.Products.ProductRepository.ProductRepository;
import sims.sims.Products.entity.Product;
import sims.sims.Products.entity.pojos.ProductPojo;
import sims.sims.Products.utils.CustomResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> ViewProducts() {
        List<Product> products = new ArrayList<>();
        try {
            System.out.println("FINDING ALL PRODUCTS FROM DATABASE");
            products = productRepository.findAll();
            System.out.println("DATABASE CALLED: FOUND PRODUCTS: " + products.size());
            System.out.println("VIEW FUNCTION COMPLETED");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("EXCEPTION : " + e.getMessage());
        }
        return products;
    }

    public String AddProduct(Product product) {
        String response;
        try {
            if (product.getProductId() != null) {
                Optional<Product> productOptional = productRepository.findProductByProduct_ID(product.getProductId());
                if (productOptional.isPresent()) {
                    response = "Product already exists with same product ID";
                } else {
                    Product myProduct = new Product();
                    if (validate(product)) {
                        myProduct.setProductId(product.getProductId());
                        myProduct.setProductName(product.getProductName());
                        myProduct.setQuantity(product.getQuantity());
                        myProduct.setPrice(product.getPrice());
                        productRepository.save(myProduct);
                        response = "Product Added Successfully";
                    } else {
                        response = "Please Input data in correct form!";
                    }
                }
            } else {
                System.out.println(product.getProductId());
                response = "Invalid Product ID";
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getLocalizedMessage();
        }
        return response;
    }

    public String RemoveProduct(Product product) {
        String response;
        try {
            if (product.getProductId() != null) {
                System.out.println("FINDING PRODUCT");
                Optional<Product> productOptional = productRepository.findProductByProduct_ID(product.getProductId());
                if (productOptional.isPresent()) {
                    System.out.println("PRODUCT FOUND, DELETING NOW");
                    Integer id = productOptional.get().getID();
                    productRepository.deleteById(id);
                    System.out.println("ID is : " + id);
                    response = "Product is Removed Successfully";
                } else {
                    response = "Product Does Not Exist";
                }
            } else {
                System.out.println(product.getProductId());
                response = "Invalid Product ID";
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getLocalizedMessage();
        }
        return response;
    }

    public String UpdateProduct(Product product) {
        String response;
        try {
            if (product.getProductId() != null) {
                Optional<Product> productOptional = productRepository.findProductByProduct_ID(product.getProductId());
                if (productOptional.isPresent()) {
                    Product product1 = new Product();
                    product1 = productOptional.get();
                    if (product.getProductName() != null) {
                        product1.setProductName(product.getProductName());
                        System.out.println("Product Name being Updated...");
                    }
                    if (product.getPrice() != null) {
                        product1.setPrice(product.getPrice());
                        System.out.println("Price being Updated...");
                    }
                    if (product.getPrice() != null) {
                        product1.setQuantity(product.getQuantity());
                        System.out.println("Quantity being Updated...");
                    }
                    productRepository.save(product1);
                    response = "Product Updated Successfully";
                } else {
                    response = "Product Does Not Exist";
                }
            } else {
                System.out.println(product.getProductId());
                response = "Invalid Product ID";
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getLocalizedMessage();
        }
        return response;
    }

    private boolean validate(Product product) {
        if (product.getProductId() != null) {
            if (validateProductId(product.getProductId())) {
                if (product.getProductName() != null) {
                    if (product.getQuantity() != null && product.getQuantity() > 0) {
                        if (product.getPrice() != null && product.getPrice() > 0) {
                            return true;
                        } else {
                            System.out.println("Invalid Price : " + product.getPrice());
                            return false;
                        }
                    } else {
                        System.out.println("Invalid Quantity : " + product.getQuantity());
                        return false;
                    }
                } else {
                    System.out.println("Invalid Product Name : " + product.getProductName());
                    return false;
                }
            } else {
                System.out.println("Invalid Product ID : " + product.getProductId());
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean validateProductId(String product_id) {
        return product_id.length() >= 2;
    }

    public String purchaseProduct(Product product) {
        String response;
        Integer totalPrice = 0;
        Integer quantity;
        try {
            if (product.getProductName() != null) {
                Optional<Product> productOptional = productRepository.findProductByProduct_Name(product.getProductName());
                if (productOptional.isPresent()) {
                    Product product1 = new Product();
                    product1 = productOptional.get();
                    System.out.println("PRODUCT FOUND, TAKING PRICE");
                    totalPrice = totalPrice + productOptional.get().getPrice();
                    quantity = productOptional.get().getQuantity();
                    if (quantity == 0)
                    {
                        response = "Out of Stock";
                    } else {
                        quantity--;
                        product1.setQuantity(quantity);
                        productRepository.save(product1);
                        response = "Purchase Complete! Total bill =";
                    }
                } else {
                    response = "Product not found";
                }
            } else {
                response = "Product name is null";
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getLocalizedMessage();
        }
        return response + " " + totalPrice;
    }

    public ResponseEntity<Object> purchaseMultipleProjects(List<Product> products){
        String response = "";
        int bill = 0;
        HashMap<String,Object> responseMap = new HashMap<>();
        List<ProductPojo> productPojo = new ArrayList<>();
        try{
            for (Product p : products) {
                ProductPojo pojo = new ProductPojo();
                Product productInDb = productRepository.findProductWhereProductNameIs(p.getProductName());
                Integer purchaseQty = p.getQuantity();
                Integer quantityInDb = productInDb.getQuantity();
                if (p.getQuantity() > 0 && p.getProductName() != null)
                {
                    if (quantityInDb != null) {
                        if(quantityInDb <= purchaseQty){
                            Integer setQuantity = productRepository.setQuantityWhereProductNameIs(quantityInDb - purchaseQty,p.getProductName());
                            pojo.setProductName(p.getProductName());
                            pojo.setQuantity(setQuantity);
                            pojo.setMessage("Quantity purchase : " + purchaseQty);
                            pojo.setPrice(p.getPrice());
                            pojo.setBilledAmount(calculateBill(productInDb.getPrice(),p.getQuantity()));
                            productPojo.add(pojo);
                            bill = bill + pojo.getBilledAmount();
                        } else {
                            pojo.setProductName(p.getProductName());
                            pojo.setMessage("Purchase Qty exceeds stock Qty, Stock Availability :  " + quantityInDb);
                            productPojo.add(pojo);

                        }
                    }else {
                        pojo.setProductName(p.getProductName());
                        pojo.setMessage("Quantity is missing!");
                        productPojo.add(pojo);
                    }
                } else {
                    response= "Please Enter Data Correctly!";
                }
            }

            responseMap.put("Product List",productPojo);
            responseMap.put("Total billed amount", bill);
        }
        catch (Exception e){
            e.printStackTrace();
            response = e.getLocalizedMessage();
        }
        return ResponseEntity.ok(new ResponseEntity<>(responseMap, HttpStatus.OK));
    }

    private Integer calculateBill(Integer price, Integer quantity) {
        return price * quantity;
    }
}