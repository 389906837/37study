package com.cloud.cang.message.common;

import com.cloud.cang.message.exception.ProtocolException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFEncryption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * PDF 工具
 * @author zhouhong
 * @version 1.0
 */
public class PdfUtils {

    private static final Logger logger = LoggerFactory.getLogger(PdfUtils.class);
    public static void generateToFile(String pdfContent, String outputFile,String password)  {
       
        OutputStream out = null;
        try {
            ITextRenderer render = new ITextRenderer();
            // 添加字体，以支持中文
        //    String parentDir = PdfUtils.class.getResource("").toString();
            render.getFontResolver().addFont(
                    SystemUtils.IS_OS_WINDOWS ? "C:/WINDOWS/Fonts/simsun.ttc" : "/usr/local/lib/SIMSUNB_linux.ttf",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
           
            if(!StringUtils.isEmpty(password)){
                
                PDFEncryption  encrypt = new PDFEncryption(password.getBytes(), "p2p58066815".getBytes(), PdfWriter.ALLOW_SCREENREADERS);
                render.setPDFEncryption(encrypt);
            }
            File f = new File(outputFile);
            File parent =f.getParentFile();
            if(!parent.exists()){
                parent.mkdirs();
            }
            out = new FileOutputStream(outputFile);
           // System.out.println( new URI("D:\\filebak\\").toURL().toString());
          
            render.setDocumentFromString(pdfContent);
            render.layout();
            render.createPDF(out);
            render.finishPDF();
        } catch (Exception e) {
            logger.error("生成pdf失败",e);
            throw new ProtocolException("生成PDF失败");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }

        }
    }

    public static void main(String[] args) {
        // System.out.println(PdfUtils.class.getResource("").gett );
        try {
            String str=(FileUtils.readFileToString(new File("E:\\xieyi.html"),"utf-8"));
        	//org.jsoup.nodes.Document doc = Jsoup.connect("http://hrb.com:8090/hrb-mbr/protocol/previewOrderProtocol/XM20160721000261").get();
        	generateToFile(str, "E:\\abc11.pdf","123456");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
