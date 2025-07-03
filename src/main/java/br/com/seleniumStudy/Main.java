package br.com.seleniumStudy;


import br.com.seleniumStudy.model.Product;
import br.com.seleniumStudy.scrap.AmazonScrap;
import br.com.seleniumStudy.service.CsvService;
import br.com.seleniumStudy.service.EmailService;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            logger.info("Iniciando pesquisa por produtos...");

            List<Product> products = AmazonScrap.findProducts("Mesa de escritório");

            System.out.println("Produtos encontrados: ");
            products.forEach(System.out::println);

            logger.info("Produtos encontrados. Iniciando geração de csv...");

            String filePath =CsvService.createCsv(products);
            File file = new File(filePath);

            logger.info("Csv gerado com sucesso! Enviando para email...");

            EmailService.sendEmail(file);

            logger.info("Email enviado com sucesso!");
        } catch (InterruptedException e) {
            logger.error("O navegador foi interrompido durante o processo", e);
        } catch(FileNotFoundException e) {
            logger.error("Ocorreu um erro durante a geração do csv", e);
        }
        catch (Exception e) {
            logger.error("Ocorreu um erro durante o processo", e);
        }
    }
}