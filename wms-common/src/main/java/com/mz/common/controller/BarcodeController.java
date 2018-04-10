package com.mz.common.controller;

import com.mz.common.util.BarcodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    /**
     * 生成条形码
     * @param barcode
     * @param response
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public void create(String barcode, HttpServletResponse response) {
        //MatrixToImageWriter.createQr(barcode, width, height, BarcodeFormat.CODE_39, "png", response);
        BarcodeUtil.getQrImage(barcode, response, "png");
    }
}
