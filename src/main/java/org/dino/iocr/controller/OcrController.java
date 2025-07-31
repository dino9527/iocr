package org.dino.iocr.controller;

import org.dino.iocr.service.IdCardOcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Ocr 请求处理器
 *
 * @author Ethan Chu
 * @since 2025/07/24 16:04
 */
@RestController
@RequestMapping("/ocr")
public class OcrController {
    @Autowired
    private IdCardOcrService idCardOcrService;

    @PostMapping("")
    public ResponseEntity<Map<String, String>> ocr(MultipartFile file) {
        return ResponseEntity.ok(idCardOcrService.idCardFront(file));
    }

    @PostMapping("/front")
    public ResponseEntity<Map<String, String>> front(MultipartFile file) throws Exception {
        return ResponseEntity.ok(idCardOcrService.idCardFront(file.getBytes()));

    }

}
