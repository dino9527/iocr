package org.dino.iocr.service;

import com.benjaminwan.ocrlibrary.OcrResult;
import com.benjaminwan.ocrlibrary.TextBlock;
import org.dino.iocr.config.properties.FileStoragePathBean;
import org.dino.iocr.utils.IdCardOcrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.dino.iocr.config.OcrConfig.ENGINE;
import static org.dino.iocr.config.OcrConfig.PARAM_CONFIG;

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
    public final static String OS_NAME = System.getProperty("os.name");

    /**
     * 身份证正面 OCR 处理方法
     *
     * @param file 身份证正面图片文件
     * @return 处理后的身份证信息
     */
    public Map<String, String> idCardFront(MultipartFile file) {
        String path = uploadFile(file);
        OcrResult ocrResult = ENGINE.runOcr(path, PARAM_CONFIG);
        ArrayList<TextBlock> textBlocks = ocrResult.getTextBlocks();
        String cardNumber = IdCardOcrUtils.cardNumber(textBlocks);
        Map<String, String> userInfoMap = new HashMap<>();
        userInfoMap.put("name", IdCardOcrUtils.predictName(textBlocks));
        userInfoMap.put("nation", IdCardOcrUtils.national(textBlocks));
        userInfoMap.put("address", IdCardOcrUtils.address(textBlocks));
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
