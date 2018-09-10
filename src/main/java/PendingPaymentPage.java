import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PendingPaymentPage {

    private final static String fileName = "20180910033804-pending_payment_list.pdf"; // only one Red Payment - Pending Payments page

    public void verifyExportedPDF() {
        List<List<String>> expectedData = getValueInRows();
        List<String> headerColumnNames = getHeaderColumnNames();
        String expectedPDFHeader =  "Pending Payments Overview";

        PDFObject pdfFile = new PDFObject(FileUtils.getLastDownloadedFile("-pending_payment_list", "pdf"));
        List<String> internalText = pdfFile.getInternalText();
        boolean containsHeader = pdfFile.containsHeader(expectedPDFHeader);
        // ASSERT containsHeader that(pdfFile.containsHeader(expectedPDFHeader)).isTrue()
        PDFObject paymentsTable = getPaymentsTable(pdfFile);

        // prepare Map for values in rows <columnName> -> <value>
        List<Map<String, String>> expectedPaymentDataList = mapHeadersToData(headerColumnNames, expectedData);

        for (Map<String, String> paymentDataMap : expectedPaymentDataList) {
            for (String key : paymentDataMap.keySet()) {
                String value = paymentDataMap.get(key);
                if (value.contains("\n")) {
                    paymentDataMap.get(key).split("\n");
                } else {

                }
            }
        }


        System.out.println("something");
    }

//    "Red Payment Slip Sep 10, 2018 InFavorOf This Guy Single Payment CH0588880100100842002  CHF 7.30 In-Work"

    private PDFObject getPaymentsTable(PDFObject pdfObject) {
        String tableHeaderRegex = "Payment type Execution date Beneficiary.*";
        String tableFooterRegex = "Total\\(\\d+\\).*";
        return pdfObject.getDataBetweenLinesMatch(tableHeaderRegex, tableFooterRegex);
    }

    private List<List<String>> getValueInRows() {
        List<List<String>> expectedData = new ArrayList<>();
        List<String> redPaymentData = Arrays.asList("", "", "Sep 10, 2018", "InFavorOf This Guy\n50-56167-0\nSingle Payment", "CH05 8888 0100 1008 4200 2", "CHF 7.30", "In-Work", "");
        expectedData.add(redPaymentData);
        return expectedData;
    }

    private List<String> getHeaderColumnNames() {
        return Arrays.asList("", "Payment type", "Execution date", "Beneficiary\nBeneficiary Account\nType", "Debit account\nPayment reason", "Amount", "State", "");
    }

    private List<Map<String, String>> mapHeadersToData(List<String> headerColumnNames, List<List<String>> expectedData) {
        List<Map<String, String>> expectedDataList = new ArrayList<>();
        for (List<String> expectedPaymentData : expectedData) {
            Map<String, String> paymentDataMap = zipToMap(
                    headerColumnNames.subList(1, headerColumnNames.size() - 1), // keys
                    expectedPaymentData.subList(1, expectedPaymentData.size() -1 )); // values
            expectedDataList.add(paymentDataMap);
        }
        return expectedDataList;
    }

    public static Map<String, String> zipToMap(List<String> keys, List<String> values) {
        return IntStream.range(0, keys.size()).boxed()
                .collect(Collectors.toMap(keys::get, values::get));
    }

}
