package com.example.posscanner;

public class Products {
    public String p_name;
    public String b_name;
    public String BarCode;
    public String price;
    public String quantity;
    public int NoOfProduct;

    //    public Products(  String b_name,String p_name, String quantity, String price)
//    {
//
//        this.p_name = p_name;
//        this.b_name = b_name;
//        this.price  = price;
//        this.quantity = quantity;
//
//    }
    public Products( String Barcode, String b_name,String p_name, String quantity, String price)
    {

        this.BarCode=Barcode;
        this.p_name = p_name;
        this.b_name = b_name;
        this.price  = price;
        this.quantity = quantity;
        this.NoOfProduct=1;

    }

    public Products(String p_name, int quantity, String price)
    {

        this.p_name = p_name;
        this.price  = price;
        this.NoOfProduct=quantity;

    }



    public String getBarCode()
    {
        return BarCode;
    }


    public String getP_name(){
        return p_name;
    }
    public String getB_name(){
        return b_name;
    }

    public String getPrice(){
        return price;
    }

    public String getQuantity(){
        return quantity;
    }
}
