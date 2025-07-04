package br.com.seleniumStudy;


import br.com.seleniumStudy.model.Product;
import br.com.seleniumStudy.scrap.AmazonScrap;
import br.com.seleniumStudy.service.CsvService;
import br.com.seleniumStudy.service.EmailService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        try {

            List<Product> products = AmazonScrap.findProducts("Mesa de escritório");

            System.out.println("Produtos encontrados: ");
            products.forEach(System.out::println);


            String filePath =CsvService.createCsv(products);
            File file = new File(filePath);


            EmailService.sendEmail(file);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}