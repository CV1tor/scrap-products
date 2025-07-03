package br.com.seleniumStudy.service;

import br.com.seleniumStudy.model.Product;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CsvService {
    private static final Dotenv dotenv = Dotenv.load();

    public static String createCsv(List<Product> products) throws InterruptedException, IOException {
        String fileName = "products" + System.currentTimeMillis();
        File file =  File.createTempFile(fileName , ".csv");

        try(PrintWriter writer = new PrintWriter(file)) {;
            writer.println("name,price");
            for (Product product : products) {
                writer.println(
                        product.getName().replace(",", ".")
                        + ","
                        + product.getPrice()
                );
            }
        } catch(FileNotFoundException e) {
            throw new FileNotFoundException();
        }
        return file.getAbsolutePath();
    }
}
