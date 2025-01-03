package com.example.posscanner;



    public class ProductDetail {

        public String BarcodeNUMBER;
        public String brandName;
        public String productName;
        public String quantity;
        public String price;
        public String NumberOfProduct;

        public ProductDetail(String BarcodeNUMBER,String brandName, String productName, String quantity, String price,String NumberOfProduct) {
            this.BarcodeNUMBER = BarcodeNUMBER;
            this.brandName = brandName;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
            this.NumberOfProduct = NumberOfProduct;
        }
        public ProductDetail(String brandName, String productName, String quantity, String price, double v) {
            this.brandName = brandName;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;

        }

        // Getter methods for accessing the fields
        public String getBarcodeNUMBER() {
            return BarcodeNUMBER;
        }
        public String getBrandName() {
            return brandName;
        }

        public String getProductName() {
            return productName;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getPrice() {
            return price;
        }

        public String getNumberOfProduct() {
            return NumberOfProduct;
        }

}
