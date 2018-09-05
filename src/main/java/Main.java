import java.io.File;

public class Main {

    private final static String filePath = "C:\\Users\\vitalii.onufryk\\Downloads\\PDF_Payments";
    private final static String fileName = "20180831144136-payment_details.pdf";

    public static void main(String[] args) {
        System.out.println("Start");

        File pdfFile = new File(filePath + File.separator + fileName);
        String[] parsedPDFLines = FileUtils.parsePDF(pdfFile);

        System.out.println("End");
    }

}