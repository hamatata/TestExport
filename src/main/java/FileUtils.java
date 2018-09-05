import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class FileUtils {

    public static File getLastDownloadedFile() {
        String downloadDir = System.getProperty("user.home") + File.separator + "Downloads";
        File files = new File(downloadDir);
        Optional<File> mostRecentFile = Arrays.stream(files.listFiles())
                .filter(File::isFile)
                .max(Comparator.comparingLong(File::lastModified));
        return mostRecentFile.get();
    }

    public static String[] parsePDF(File pdfFile) {
        String[] lines = new String[0];
        try {
            PDDocument document = PDDocument.load(pdfFile);
            String pdfFileInText = new PDFTextStripper().getText(document);
            lines = pdfFileInText.split("\\r?\\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static Optional<String> verifyHeader(String[] parsedPDF, String expectedHeader) {
        return Arrays.stream(parsedPDF).filter(x -> x.equals(expectedHeader)).findFirst();
    }

}
