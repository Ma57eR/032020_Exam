package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportPicturesDto;
import softuni.exam.models.entity.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String PICTURES_FILE = "src/main/resources/files/json/pictures.json";
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final CarService carService;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, CarService carService) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.carService = carService;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files
                .readString(Path.of(PICTURES_FILE));
    }

    @Override
    public String importPictures() throws IOException {

        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson.fromJson(readPicturesFromFile(), ImportPicturesDto[].class))
                .filter(importPicturesDto -> {
                   boolean isValid = validationUtil.isValid(importPicturesDto);
                   sb.append(isValid ? String.format("Successfully impoerted %s",
                           importPicturesDto.getName())
                           : "Invalid picture")
                           .append(System.lineSeparator());

                   return isValid;
                })
                .map(importPicturesDto -> {
                    Picture picture = modelMapper.map(importPicturesDto, Picture.class);
                    picture.setCar(carService.findById(importPicturesDto.getCar()));
                    return picture;
                })
                .forEach(pictureRepository::save);

//        Arrays.stream(importPicturesDtos)
//                .filter(importPicturesDto -> {
//                    boolean isValid = validationUtil.isValid(importPicturesDto);
//                    if (!isValid) {
//                        sb.append("Invalid picture").append(System.lineSeparator());
//                    } else {
//                        sb.append("Successfully import picture ").append(importPicturesDto.getName()).append(System.lineSeparator());
//                    }
//                    return isValid;
//                })
//                .map(importPicturesDto -> {
//                    Picture picture = modelMapper.map(importPicturesDto, Picture.class);
//                    picture.setCar(carService.findById(importPicturesDto.getCar()));
//                    return picture;
//                })
//                .forEach(pictureRepository::save);

        return sb.toString().trim();
    }
}
