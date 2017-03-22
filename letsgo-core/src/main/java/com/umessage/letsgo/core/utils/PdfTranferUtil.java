package com.umessage.letsgo.core.utils;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by myz on 2016/10/17.
 * 生成pdf缩略图
 */
public class PdfTranferUtil {

      private static Logger logger = Logger.getLogger(CreatePdfUtil.class);

//    jpedal  中文生成缩略图不好使
//    public static final String FILETYPE_JPG = "jpg";
//    public static final String SUFF_IMAGE ="."+ FILETYPE_JPG;
//    public static void tranfer(String filepath,String imagepath,float zoom) throws Exception{
//       //ICEPDF-4.2.2 （最新有4.3了）
//        Document document = null;
//        float rotation = 0f;
//        document = new Document();
//        document.setFile(filepath);
//        // maxPages = document.getPageTree().getNumberOfPages();
//        BufferedImage img = (BufferedImage) document.getPageImage(0,
//                GraphicsRenderingHints.SCREEN, PrimitivePropertyBuilders.Page.BOUNDARY_CROPBOX, rotation,
//                zoom);
//        Iterator iter = ImageIO.getImageWritersBySuffix(FILETYPE_JPG);
//        ImageWriter writer = (ImageWriter) iter.next();
//        File outFile = new File(imagepath);
//        FileOutputStream out = new FileOutputStream(outFile);
//        ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
//        writer.setOutput(outImage);
//        writer.write(new IIOImage(img, null, null));
//    }

    //pdfbox 添加jar包  thumbnail; 图片的名字 pngid.png
      public static void tranfer(String pdfUrl,String thumbnailUrl)  throws Exception{

        logger.info("生成pdf缩略图的地址："+thumbnailUrl);
        File file = new File(pdfUrl);
        PDDocument doc = PDDocument.load(file);
        PDFRenderer renderer = new PDFRenderer(doc);
        int pageCount = doc.getNumberOfPages();
        logger.info("pageCount"+pageCount);
        for(int i=0;i<1;i++){
// //       BufferedImage image = renderer.renderImageWithDPI(i, 296);
            BufferedImage image = renderer.renderImage(i, 2.5f);
            ImageIO.write(image, "PNG", new File(thumbnailUrl));
            logger.info("生成pdf缩略图完毕ending");
        }
          doc.close();
    }
}
