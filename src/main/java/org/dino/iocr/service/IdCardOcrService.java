package org.dino.iocr.service;

import com.benjaminwan.ocrlibrary.OcrResult;
import com.benjaminwan.ocrlibrary.TextBlock;
import io.github.hzkitty.RapidOCR;
import io.github.hzkitty.entity.OcrConfig;
import io.github.hzkitty.entity.RecResult;
import io.github.mymonstercat.ocr.InferenceEngine;
import io.github.mymonstercat.ocr.config.ParamConfig;
import lombok.SneakyThrows;
import org.dino.iocr.config.properties.FileStoragePathBean;
import org.dino.iocr.utils.IdCardOcrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 身份证 OCR 服务类
 *
 * @author Ethan Chu
 * @since 2025/07/28 11:23
 */
@Service
public class IdCardOcrService {

    @Autowired
    private FileStoragePathBean fileStoragePathBean;
    @Autowired
    private InferenceEngine inferenceEngine;
    @Autowired
    private ParamConfig paramConfig;
    @Autowired
    private OcrConfig ocrConfig;
    public final static String OS_NAME = System.getProperty("os.name");

    /**
     * 身份证正面 OCR 处理方法
     *
     * @param file 身份证正面图片文件
     * @return 处理后的身份证信息
     */
    public Map<String, String> idCardFront(MultipartFile file) {
        String path = uploadFile(file);
        OcrResult ocrResult = inferenceEngine.runOcr(path, paramConfig);
        ArrayList<TextBlock> textBlocks = ocrResult.getTextBlocks();
        List<String> textList = textBlocks.stream().map(TextBlock::getText).collect(Collectors.toList());
        return response(textList);
    }

    /**
     * 身份证正面 OCR 处理方法
     *
     * @param bytes 身份证正面图片字节数组
     * @return 处理后的身份证信息
     */
    @SneakyThrows
    public Map<String, String> idCardFront(byte[] bytes) {
        RapidOCR rapidOCR = RapidOCR.create(ocrConfig);
        io.github.hzkitty.entity.OcrResult ocrResult = rapidOCR.run(bytes);
        List<String> textList = ocrResult.getRecRes().stream().map(RecResult::getText).collect(Collectors.toList());
        return response(textList);
    }

    private Map<String, String> response(List<String> textList) {
        String cardNumber = IdCardOcrUtils.cardNumber(textList);
        Map<String, String> userInfoMap = new HashMap<>();
        userInfoMap.put("name", IdCardOcrUtils.predictName(textList));
        userInfoMap.put("nation", IdCardOcrUtils.national(textList));
        userInfoMap.put("address", IdCardOcrUtils.address(textList));
        userInfoMap.put("cardNumber", cardNumber);
        userInfoMap.put("sex", IdCardOcrUtils.sex(cardNumber));
        userInfoMap.put("birthday", IdCardOcrUtils.birthday(cardNumber));
        return userInfoMap;
    }

    private String uploadFile(MultipartFile file) {
        String filePathForSystem = fileStoragePathBean.getFilePathForSystem(OS_NAME);
        File folder = new File(filePathForSystem);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        // 对上传的文件重命名, 避免文件重名
        String oldName = file.getOriginalFilename();
        String newName = this.usingUUID() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        try {
            // 文件保存
            file.transferTo(new File(folder, newName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String path = folder.getPath() + File.separator + newName;
        return path;
    }

    private String usingUUID() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("-", "");
    }


}
