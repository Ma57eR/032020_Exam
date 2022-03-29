package softuni.exam.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Seller;

import javax.xml.bind.JAXBException;
import java.io.IOException;


public interface SellerService{
    
    boolean areImported();

    String readSellersFromFile() throws IOException;

    String importSellers() throws IOException, JAXBException;

}
