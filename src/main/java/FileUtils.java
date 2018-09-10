import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    private static final String DOWNLOAD_DIR = System.getProperty("user.home") + File.separator + "Downloads";

    public static File getLastDownloadedFile() {
        File files = new File(DOWNLOAD_DIR);

        // TODO: make it work if file pattern is not specified
        String fileExtension = "pdf";
        String fileNamePart = "-payment_details";
        String expectedFilePattern = ".*" + fileNamePart + ".*" + "\\." + fileExtension;

        // filter all files that match expectedFilePattern
        List<File> matchedFiles = Arrays.stream(files.listFiles())
                .filter(File::isFile)
                .filter(f -> f.getName().matches(expectedFilePattern))
                .collect(Collectors.toList());

        // get most recent file from
        return matchedFiles.stream()
                .max(Comparator.comparingLong(File::lastModified))
                .orElse(null);
    }

    public static File getLastDownloadedFile(String fileNamePart, String fileExtension) {
        String expectedFilePattern = ".*" + fileNamePart + ".*" + "\\." + fileExtension;
        return getDownloadedFiles().stream().filter(File::isFile)
                .filter(f -> f.getName().matches(expectedFilePattern))
                .max(Comparator.comparingLong(File::lastModified))
                .orElse(null);
    }

    private static List<File> getDownloadedFiles() {
        return Arrays.asList(new File(DOWNLOAD_DIR).listFiles());
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

    public static boolean verifyHeader(String[] parsedPDF, String expectedHeader) {
        return Arrays.stream(parsedPDF).anyMatch(x -> x.equals(expectedHeader));
    }

}
