import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class PDFObject {

    private String header;
    private int headerLineNum;
    private List<String> internalText;

    public PDFObject(List<String> text) {
        internalText = text;
    }

    public PDFObject(File pdfFile) {
        try {
            PDDocument document = PDDocument.load(pdfFile);
            internalText = Arrays.asList(new PDFTextStripper().getText(document).split("\\r?\\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLineNumEquals(String text) {
        return internalText.indexOf(text);
    }

    public int getLineNumContains(String text) {
        String element = internalText.stream().filter(line -> line.contains(text)).findFirst().orElse("no such element");
        return getLineNumEquals(element);
    }

    public int getLineNumMatches(String regex) {
        return IntStream.range(0, internalText.size())
                .filter(index -> internalText.get(index).matches(regex))
                .findFirst().orElse(-1);
    }

    public boolean containsHeader(String expectedHeader) {
        return internalText.stream().anyMatch(x -> x.equals(expectedHeader));
    }

    public List<String> getDataBetweenHeaders(String expectedFileHeader, String paymentHistoryHeader) {
        int startIndex = getLineNumEquals(expectedFileHeader);
        int endIndex = getLineNumEquals(paymentHistoryHeader);
        return internalText.subList(startIndex + 1, endIndex);
    }

    public PDFObject getDataBetweenLinesMatch(String line1_regex, String line2_regex) {
        return new PDFObject(internalText.subList(getLineNumMatches(line1_regex) + 1, getLineNumMatches(line2_regex)));
    }

    public List<String> getInternalText() {
        return internalText;
    }

    public void verifyInternalText(List<String> qrBillPaymentDetailsExpected) {
//        for (String line : qrBillPaymentDetailsExpected) {
//
//        }
//        internalText.stream()
    }
}
