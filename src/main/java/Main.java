import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

//    private final static String filePath = "C:\\Users\\vitalii.onufryk\\Downloads\\PDF_Payments";
    private final static String filePath = System.getProperty("user.home") + File.separator + "Downloads";
//    private final static String fileName = "20180831144136-payment_details.pdf";
    private final static String fileName = "20180909233700-payment_details.pdf"; // QR-Bill - Payment Details page

    public static void main(String[] args) {
        System.out.println("--- Start ---");
        // String exportDataExpected = qrBillPreview.getExportData(); // get export data from Payment Overview page
        String qrBillPaymentDetailsExpected = "Debit account\nCH32 8888 0100 1008 4200 1 pos_LET.01 1 CHF 9,528,946.29\nIBAN\nCH75 8132 3000 0022 1739 0\nBeneficiary name\nTKHdxJCQIg\nBeneficiary address\nAllmendstrasse\nBeneficiary address no.\n140\nBeneficiary postal code\n8041\nBeneficiary locality\nZuerich\nBeneficiary country\nSwitzerland\nBIC\n81323\nBank name\nRaiffeisenbank Wittenbach\nBank address\n21 Hauptstrasse\n8750 Glarus\nSwitzerland\nCurrency\nCHF\nAmount\n5.44\nBooking text\nqtINSoJmjIJhWUcivpJz\nAdvice\nNo advice\nExecution\nSingle Order\nExecution date\nSep 10, 2018\nStatus\nApproved";
        // qrBillPreview.verifyExportedPDF(exportDataExpected); // expected header on this page will be "Payment details"
        //          this is the method down right here
        // inside qrBillPreview --> method verifyExportedPDF(String exportDataExpected) -->
        //              verify header "Payment details"
        //              verify 'exportDataExpected'

        new PendingPaymentPage().verifyExportedPDF();
        System.out.println("--- End ---");
        System.exit(0);

        File pdfFile = new File(filePath + File.separator + fileName); // uncomment if the file path is hardcoded
        String[] parsedPDFLines = FileUtils.parsePDF(pdfFile);
        String expectedHeader = "Payment details";
        boolean headerVerifyResult = FileUtils.verifyHeader(parsedPDFLines, expectedHeader);

        String[] parts = qrBillPaymentDetailsExpected.split("\n");
        List<String> listItems = new ArrayList<>();
        for (int i = 0; i < parts.length; i =i+2) {
            listItems.add(parts[i]+"\n"+parts[i+1]);
        }

        System.out.println("--- End ---");
    }

    // method in Payment Details (Overview) page object (or steps?)
//    public static void verifyExportedPDF() {
//        List<String> expectedData = getExportData();
//
//        // get last downloaded file with name "-payment_details.pdf" in the end
//        File pdfFile = FileUtils.getLastDownloadedFile();
//        // create PDFObject (parse, etc.)
//        PDFObject pdfToVerify = new PDFObject(pdfFile);
//
//        String expectedFileHeader = "Payment details";
//        boolean containsHeader = pdfToVerify.containsHeader(expectedFileHeader);
//
//        int fileHeaderNum = pdfToVerify.getLineNumEquals(expectedFileHeader);
//        String paymentHistoryHeader = "Modification Date Contract Description Signature Percentage";
//        int paymentHistoryHeaderNum = pdfToVerify.getLineNumEquals(paymentHistoryHeader);
//
//        pdfToVerify = pdfToVerify.getDataBetweenHeaders(expectedFileHeader, paymentHistoryHeader);
////        String actualText = String.join(" ", pdfToVerify.getInternalText());
////        String expectedText = "Debit account\nCH32 8888 0100 1008 4200 1 pos_LET.01 1 CHF 9,528,946.29\nIBAN\nCH75 8132 3000 0022 1739 0\nBeneficiary name\nTKHdxJCQIg\nBeneficiary address\nAllmendstrasse\nBeneficiary address no.\n140\nBeneficiary postal code\n8041\nBeneficiary locality\nZuerich\nBeneficiary country\nSwitzerland\nBIC\n81323\nBank name\nRaiffeisenbank Wittenbach\nBank address\n21 Hauptstrasse\n8750 Glarus\nSwitzerland\nCurrency\nCHF\nAmount\n5.44\nBooking text\nqtINSoJmjIJhWUcivpJz\nAdvice\nNo advice\nExecution\nSingle Order\nExecution date\nSep 10, 2018\nStatus\nApproved";
//        System.out.println("END");
//
////        pdfToVerify.verifyInternalText(qrBillPaymentDetailsExpected);
//    }

    public static List<String> getExportData() {
        // String exportDataExpected = qrBillPreview.getExportData(); // get export data from Payment Overview page
        String qrBillPaymentDetailsExpected = "Debit account\nCH32 8888 0100 1008 4200 1 pos_LET.01 1 CHF 9,528,946.29\nIBAN\nCH75 8132 3000 0022 1739 0\nBeneficiary name\nTKHdxJCQIg\nBeneficiary address\nAllmendstrasse\nBeneficiary address no.\n140\nBeneficiary postal code\n8041\nBeneficiary locality\nZuerich\nBeneficiary country\nSwitzerland\nBIC\n81323\nBank name\nRaiffeisenbank Wittenbach\nBank address\n21 Hauptstrasse\n8750 Glarus\nSwitzerland\nCurrency\nCHF\nAmount\n5.44\nBooking text\nqtINSoJmjIJhWUcivpJz\nAdvice\nNo advice\nExecution\nSingle Order\nExecution date\nSep 10, 2018\nStatus\nApproved";
        qrBillPaymentDetailsExpected = qrBillPaymentDetailsExpected.replaceFirst("\n", " ").replaceAll("(\n.*?)\n", "$1 ");
        return Arrays.asList(qrBillPaymentDetailsExpected.split("\\n"));
    }

}