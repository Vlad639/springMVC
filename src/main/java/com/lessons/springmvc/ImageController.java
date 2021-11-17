package com.lessons.springmvc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;


@Controller
public class ImageController {

    private static final Logger logger = LogManager.getLogger(ImageController.class);

    @RequestMapping(value = "/blackWhiteImage", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public String
    makeImageBlackWhite(@RequestParam("image") MultipartFile file, Model model)  {


        model.addAttribute("message", "Failed");

        try {
            InputStream inputStream = file.getInputStream();

            String logText = "";
            logText += file.getContentType() + " ";
            logText += file.getOriginalFilename();
            logText += file.getSize();
            logger.info(logText);

                BufferedImage sourceImage = ImageIO.read(inputStream);
                int width = sourceImage.getWidth();
                int height = sourceImage.getHeight();
                BufferedImage blackWhiteImage = new BufferedImage(width, height, sourceImage.getType());

                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        Color color = new Color(sourceImage.getRGB(x, y));
                        int red = color.getRed();
                        int green = color.getGreen();
                        int blue = color.getBlue();

                        int blackWhiteCanal = (int) (red * 0.299 + green * 0.587 + blue * 0.114);

                        red = blackWhiteCanal;
                        green = blackWhiteCanal;
                        blue = blackWhiteCanal;

                        Color blackWhiteColor = new Color(red, green, blue);
                        blackWhiteImage.setRGB(x, y, blackWhiteColor.getRGB());

                    }
                }
                inputStream.close();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(blackWhiteImage, "png", byteArrayOutputStream);

                String newImageInBase64String = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());

                model.addAttribute("message", "Ok");
                model.addAttribute("base64NewImage", newImageInBase64String);

            }
            catch (Exception e){
                e.printStackTrace();
            }

        return "imageReady";
    }

    @RequestMapping(value = "/blackWhiteImage", method = RequestMethod.GET)
    public String errorPage(Model model){
        model.addAttribute("message", "Download image!");
        model.addAttribute("base64NewImage", "");
        return "imageReady";
    }
}
