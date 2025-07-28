# iocr Project Documentation

## Project Overview
`iocr` is an ID card OCR recognition project built on Spring Boot. It uses OCR technology to recognize the front side of ID cards and extract information such as name, ethnicity, address, ID number, gender, and date of birth.

## Tech Stack
- **Framework**: Spring Boot 2.7.18
- **OCR Engine**: `rapidocr` 0.0.7
- **Build Tool**: Maven
- **Programming Language**: Java 8

## Features
- **ID Card Front Recognition**: Supports uploading front images of ID cards to extract information including name, ethnicity, address, ID number, gender, and date of birth.
- **File Storage**: Supports both Windows and Linux systems, with the ability to store uploaded image files in specified paths.
- **OCR Configuration**: Provides OCR parameter tuning options, including angle detection and padding parameters.

## Platform-Specific Packaging
To package the project for `Linux x86_64` platform, execute the following command:
```bash
mvn clean package -P linux-x86_64 -Dlinux-build
```

## API Interface
ID Card Front Recognition
- **Request Method**: POST
- **Request Path**: To be determined based on actual controller implementation
- **Request Parameter**:
    - `file`: ID card front image file (MultipartFile type)
- **Response Result**:
```json
{
  "name": "Name",
  "nation": "Ethnicity",
  "address": "Address",
  "cardNumber": "ID Number",
  "sex": "Gender",
  "birthday": "Date of Birth"
}
```

## Important Notes
- Ensure that the required model files and dependencies for the OCR engine are properly configured.
- Uploaded images should be clear and legible for optimal OCR recognition results.

## Acknowledgements
- [RapidOCR](https://github.com/RapidAI/RapidOCR)
- [PaddleOCR](https://github.com/PaddlePaddle/PaddleOCR)
- [RapidOcr-Java](https://github.com/MyMonsterCat/RapidOcr-Java)
```
