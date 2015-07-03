package piccoapp;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

/**
 * Created by mtiko_000 on 6/3/2015.
 */
public class menuPrinter {

    //filepaths for html files for printing
    static final File beerList_printFile = new File(MenuChanger.HOME_DIR + "\\html\\Beer_List.html");
    static final File beerList_Window_printFile = new File(MenuChanger.HOME_DIR + "\\html\\Beer_List_Window.html");
    static final File beerList_htmlFile = new File(MenuChanger.HOME_DIR + "\\html\\beer.html");


    //headers and footers for printlist
    static final String PRINT_LIST_HTML_FOOTER_FILEPATH = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "printList_htmlFooter.txt";
    static final String PRINT_LIST_HTML_HEADER_FILEPATH = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "printList_htmlHeader.txt";
    //headers for weblist
    static final String WEB_LIST_HTML_HEADER = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "webList_htmlHeader.txt";
    static final String WEB_LIST_HTML_FOOTER = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "webList_htmlFooter.txt";
    File printList_headerFile = new File(PRINT_LIST_HTML_HEADER_FILEPATH);
    File printList_footerFile = new File(PRINT_LIST_HTML_FOOTER_FILEPATH);
    String printList_headerHTML;
    String printList_footerHTML;
    File webList_headerFile = new File(WEB_LIST_HTML_HEADER);
    File webList_footerFile = new File(WEB_LIST_HTML_FOOTER);
    String webList_footerHTML;
    String webList_headerHTML;



    public menuPrinter() {
        try {
            printList_headerHTML = FileUtils.readFileToString(printList_headerFile);
            printList_footerHTML = FileUtils.readFileToString(printList_footerFile);
            webList_footerHTML = FileUtils.readFileToString(webList_footerFile);
            webList_headerHTML = FileUtils.readFileToString(webList_headerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeBeerList(String bottledBeerListHTML, String draftBeerListHTML) {

        try {
            BufferedWriter printList_bufferedWriter = new BufferedWriter(new FileWriter(beerList_printFile));
            printList_bufferedWriter.write(printList_headerHTML);
            printList_bufferedWriter.write(bottledBeerListHTML);
            printList_bufferedWriter.write(draftBeerListHTML);
            printList_bufferedWriter.write(printList_footerHTML);
            printList_bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeWindowBeerList(String draftBeerListHTML) {

        try {
            BufferedWriter printList_bufferedWriter = new BufferedWriter(new FileWriter(beerList_Window_printFile));
            printList_bufferedWriter.write(printList_headerHTML);
            printList_bufferedWriter.write(draftBeerListHTML);
            printList_bufferedWriter.write(printList_footerHTML);
            printList_bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeWebBeerList(String bottledBeerListHTML, String draftBeerListHTML) {
        try {
            BufferedWriter printList_bufferedWriter = new BufferedWriter(new FileWriter(beerList_htmlFile));
            printList_bufferedWriter.write(webList_headerHTML);
            printList_bufferedWriter.write(draftBeerListHTML);
            printList_bufferedWriter.write(bottledBeerListHTML);
            printList_bufferedWriter.write(webList_footerHTML);
            printList_bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(MenuChanger.SERVER, MenuChanger.PORT);
            ftpClient.login(MenuChanger.USER_NAME, MenuChanger.password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            ftpClient.changeWorkingDirectory("/piccorestaurant.com/");

            String website_remoteFile = "beer.html";
            InputStream website_inputStream = new FileInputStream(beerList_htmlFile);
            boolean website_done = ftpClient.storeFile(website_remoteFile, website_inputStream);
            if (website_done) {
                System.out.println("beerList_website uploaded");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}
