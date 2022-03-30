package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferRootDto;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class OfferServiceImpl implements OfferService {

    private static final String XML_OFFERS_FILE_PATH = "src/main/resources/files/xml/offers.xml";
    private final OfferRepository offerRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CarService carService;
    private final SellerService sellerService;

    public OfferServiceImpl(OfferRepository offerRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, CarService carService, SellerService sellerService) {
        this.offerRepository = offerRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.carService = carService;
        this.sellerService = sellerService;
    }


    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        OfferRootDto offerRootDto = xmlParser
                .fromFile(XML_OFFERS_FILE_PATH, OfferRootDto.class);

        offerRootDto.getOffers()
                .stream()
                .filter(offerDto -> {
                            boolean isValid = validationUtil.isValid(offerDto);
                            sb.append(isValid ? String.format("Successfully import offer %s - %s"
                                            , offerDto.getAddedOn(), offerDto.isHasGoldStatus())
                                            : "Invalid offer")
                                    .append(System.lineSeparator());
                            return isValid;
                        }
                )
                .map(offerDto -> {
                    Offer offer = modelMapper.map(offerDto, Offer.class);
                    offer.setCar(carService.findById(offer.getCar().getId()));
                    offer.setSeller(sellerService.findById(offer.getSeller().getId()));
                    return offer;
                })
                .forEach(offerRepository::save);


        return sb.toString();
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        return null;
    }
}
