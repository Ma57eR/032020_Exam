package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportSellersDto;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class SellerServiceImpl implements SellerService {
    private static final String XML_SELLER_FILE = "src/main/resources/files/xml/sellers.xml";
    private final SellerRepository sellerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public SellerServiceImpl(SellerRepository sellerRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.sellerRepository = sellerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files
                .readString(Path.of(XML_SELLER_FILE));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        ImportSellersDto importSellersDto = xmlParser
                .fromFile(XML_SELLER_FILE, ImportSellersDto.class);

        importSellersDto.getSellers()
                .stream()
                .filter(sellerDto -> {
                    boolean isValid = validationUtil.isValid(sellerDto);

                    sb.append(isValid ? String.format("Successfully import seller %s - %s",
                            sellerDto.getLastName(), sellerDto.getEmail())
                            : "Invalid seller")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(sellerDto -> modelMapper.map(sellerDto, Seller.class))
                .forEach(sellerRepository::save);



        return sb.toString();
    }

    @Override
    public Seller findById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }
}
