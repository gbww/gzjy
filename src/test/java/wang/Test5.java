package wang;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test5 {
    public static final String SRC = "D:/result.pdf";
    public static final String DEST = "D:/results/result000.pdf";
    public static final String IMG = "D:/gongzhang.jpg";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Test5().manipulatePdf(SRC, DEST);
    }

    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.3f);
        Image image = Image.getInstance(IMG);
        PdfImage stream = new PdfImage(image, "", null);
        stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
        PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
        image.setDirectReference(ref.getIndirectReference());
        image.setAbsolutePosition(350, 50);
        int pageCount= reader.getNumberOfPages();
        PdfContentByte over = stamper.getOverContent(pageCount-1);
        over.setGState(gs1);
        over.addImage(image);
        stamper.close();
        reader.close();
    }
}
