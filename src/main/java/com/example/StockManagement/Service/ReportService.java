package com.example.StockManagement.Service;

import com.example.StockManagement.Model.Product;
import com.example.StockManagement.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ProductRepository productRepository;

    public void generateReport() throws IOException {
        List<Product> products = productRepository.findAll();
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String filePath = "src/main/resources/report/products_inventory_report_" + date + ".csv";

        try(ICsvBeanWriter beanWriter = new CsvBeanWriter(new FileWriter(filePath), CsvPreference.STANDARD_PREFERENCE)){
            String[] header = {"ProductId","ProductName","ProductPrice","Inventory"};
            beanWriter.writeHeader(header);

            for(Product product : products){
                beanWriter.write(product,header);
            }
        }
    }
}
