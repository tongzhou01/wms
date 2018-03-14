package com.mz.common.controller;

import com.google.zxing.BarcodeFormat;
import com.mz.common.util.MatrixToImageWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * 条形码，二维码
 *
 * @author tongzhou
 * @date 2018-03-14 11:41
 **/
@Controller
@RequestMapping("/barcode")
public class BarcodeController {

    @RequestMapping
    public void test(String barcode, HttpServletResponse response) {
        MatrixToImageWriter.createPayQr(barcode,400,100, BarcodeFormat.CODE_128,"jpg",response);
    }
}
