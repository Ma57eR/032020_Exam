package softuni.exam.models.dto;

import softuni.exam.models.entity.Offer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferRootDto {

    @XmlElement(name = "offer")
    List<OfferDto> offers;

    public List<OfferDto> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferDto> offers) {
        this.offers = offers;
    }
}
