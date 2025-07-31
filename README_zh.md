# iocr 项目文档

## 项目概述
`iocr` 是一个基于 Spring Boot 构建的身份证 OCR 识别项目，借助 OCR 技术对身份证正面图片进行识别，提取身份证上的姓名、民族、地址、身份证号、性别和出生日期等信息。

## 技术栈
- **框架**：Spring Boot 2.7.18
- **OCR 引擎**：`rapidocr` 0.0.7
- **构建工具**：Maven
- **编程语言**：Java 8

## 功能特性
- **身份证正面识别**：支持上传身份证正面图片，提取姓名、民族、地址、身份证号、性别和出生日期等信息。
- **文件存储**：支持 Windows 和 Linux 系统，可将上传的图片文件存储到指定路径。
- **OCR 配置**：提供 OCR 参数调优配置，可设置角度检测、填充等参数。

## 打包特定平台
若要打包 `Linux x86_64` 平台的项目，执行以下命令：
```bash
mvn clean package -P linux-x86_64 -Dlinux-build
```

## API 接口
身份证正面识别
- **请求方式**：POST
- **请求路径**：需根据实际控制器实现确定
- **请求参数**：
  - `file`：身份证正面图片文件（MultipartFile 类型）
- **响应结果**：
```json
{
  "name": "姓名",
  "nation": "民族",
  "address": "地址",
  "cardNumber": "身份证号",
  "sex": "性别",
  "birthday": "出生日期"
}
```

## 注意事项
- 确保 OCR 引擎所需的模型文件和依赖库正确配置。
- 上传的图片需清晰可辨，否则可能影响 OCR 识别效果。

## 鸣谢
- [RapidOCR](https://github.com/RapidAI/RapidOCR)
- [PaddleOCR](https://github.com/PaddlePaddle/PaddleOCR)
- [RapidOcr-Java](https://github.com/MyMonsterCat/RapidOcr-Java)
- [RapidOCR4j](https://github.com/hzkitty/RapidOCR4j)

