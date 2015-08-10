package piccoapp;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import piccoapp.lists.flavorList;
import piccoapp.menuItems.Flavor;

import java.io.*;

/**
 * Created by mtiko_000 on 6/3/2015.
 */
public class menuPrinter {

    //filepaths for html files for printing
    static final File beerList_printFile = new File(MenuChanger.HOME_DIR + "\\html\\Beer_List.html");
    static final File beerList_Window_printFile = new File(MenuChanger.HOME_DIR + "\\html\\Beer_List_Window.html");
    static final File beerList_htmlFile = new File(MenuChanger.HOME_DIR + "\\html\\beer.html");
    static final File iceCreamList_htmlFile = new File(MenuChanger.HOME_DIR + "\\html\\icecream.html");
    static final File iceCreamList_printFile = new File(MenuChanger.HOME_DIR + "\\html\\iceCream_List.html");


    //headers and footers for printlist
    static final String BEER_LIST_PRINT_HTML_FOOTER_FILEPATH = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "printList_htmlFooter.txt";
    static final String BEER_LIST_PRINT_HTML_HEADER_FILEPATH = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "printList_htmlHeader.txt";
    static final String ICE_CREAM_LIST_PRINT_HTML_HEADER_FILEPATH = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "iceCream_print_htmlHeader.txt";
    static final String ICE_CREAM_LIST_PRINT_HTML_FOOTER_FILEPATH = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "iceCream_print_htmlFooter.txt";

    //headers for weblist
    static final String BEER_LIST_WEB_HTML_HEADER = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "webList_htmlHeader.txt";
    static final String BEER_LIST_WEB_HTML_FOOTER = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "webList_htmlFooter.txt";
    static final String ICE_CREAM_LIST_WEB_HTML_HEADER_FILEPATH = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "iceCream_webList_htmlHeader.txt";
    static final String ICE_CREAM_LIST_WEB_HTML_FOOTER_FILEPATH = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "iceCream_webList_htmlFooter.txt";

    File printList_headerFile = new File(BEER_LIST_PRINT_HTML_HEADER_FILEPATH);
    File printList_footerFile = new File(BEER_LIST_PRINT_HTML_FOOTER_FILEPATH);
    String beerList_print_html_header_string;
    String beerList_print_html_footer_string;
    File webList_headerFile = new File(BEER_LIST_WEB_HTML_HEADER);
    File webList_footerFile = new File(BEER_LIST_WEB_HTML_FOOTER);
    String beerList_web_html_footer_string;
    String beerList_web_html_header_string;

    File iceCream_webList_headerHTML_file = new File(ICE_CREAM_LIST_WEB_HTML_HEADER_FILEPATH);
    File iceCream_webList_footerHTML_file = new File(ICE_CREAM_LIST_WEB_HTML_FOOTER_FILEPATH);
    String iceCreamList_web_html_header_string;
    String iceCreamList_web_html_footer_string;

    File iceCreamList_print_html_header_file = new File(ICE_CREAM_LIST_PRINT_HTML_HEADER_FILEPATH);
    File iceCreamList_print_html_footer_file = new File(ICE_CREAM_LIST_PRINT_HTML_FOOTER_FILEPATH);
    String iceCreamList_print_html_header_string;
    String iceCreamList_print_html_footer_string;


    public menuPrinter() {
        try {
            beerList_print_html_header_string = FileUtils.readFileToString(printList_headerFile);
            beerList_print_html_footer_string = FileUtils.readFileToString(printList_footerFile);
            beerList_web_html_footer_string = FileUtils.readFileToString(webList_footerFile);
            beerList_web_html_header_string = FileUtils.readFileToString(webList_headerFile);
            iceCreamList_web_html_header_string = FileUtils.readFileToString(iceCream_webList_headerHTML_file);
            iceCreamList_web_html_footer_string = FileUtils.readFileToString(iceCream_webList_footerHTML_file);
            iceCreamList_print_html_header_string = FileUtils.readFileToString(iceCreamList_print_html_header_file);
            iceCreamList_print_html_footer_string = FileUtils.readFileToString(iceCreamList_print_html_footer_file);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeBeerList(String bottledBeerListHTML, String draftBeerListHTML) {

        try {
            BufferedWriter printList_bufferedWriter = new BufferedWriter(new FileWriter(beerList_printFile));
            printList_bufferedWriter.write(beerList_print_html_header_string);
            printList_bufferedWriter.write(bottledBeerListHTML);
            printList_bufferedWriter.write(draftBeerListHTML);
            printList_bufferedWriter.write(beerList_print_html_footer_string);
            printList_bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeWindowBeerList(String draftBeerListHTML) {

        try {
            BufferedWriter printList_bufferedWriter = new BufferedWriter(new FileWriter(beerList_Window_printFile));
            printList_bufferedWriter.write(beerList_print_html_header_string);
            printList_bufferedWriter.write(draftBeerListHTML);
            printList_bufferedWriter.write(beerList_print_html_footer_string);
            printList_bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeWebBeerList(String bottledBeerListHTML, String draftBeerListHTML) {
        try {
            BufferedWriter printList_bufferedWriter = new BufferedWriter(new FileWriter(beerList_htmlFile));
            printList_bufferedWriter.write(beerList_web_html_header_string);
            printList_bufferedWriter.write(draftBeerListHTML);
            printList_bufferedWriter.write(bottledBeerListHTML);
            printList_bufferedWriter.write(beerList_web_html_footer_string);
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

    public void writeWebIceCreamList(flavorList iceCreamFlavors, flavorList sorbetFlavors) {

        String iceCreamHTML;
        String sorbetHTML;

        StringBuilder iceCreamBuilder = new StringBuilder();
        StringBuilder sorbetBuilder = new StringBuilder();

        //write ice cream list html
        for (Flavor f : iceCreamFlavors.getSortedList()) {
            iceCreamBuilder.append(f.getFlavor() + "<br/>");
        }

        //write sorbet list html
        //header for sorbet part
        sorbetBuilder.append("<br/><h1>Sorbet</h1>");

        for (Flavor f : sorbetFlavors.getSortedList()) {
            sorbetBuilder.append(f.getFlavor() + "<br/>");
        }

        iceCreamHTML = iceCreamBuilder.toString();
        sorbetHTML = sorbetBuilder.toString();

        try {
            BufferedWriter printList_bufferedWriter = new BufferedWriter(new FileWriter(iceCreamList_htmlFile));
            printList_bufferedWriter.write(iceCreamList_web_html_header_string);
            printList_bufferedWriter.write(iceCreamHTML);
            printList_bufferedWriter.write(sorbetHTML);
            printList_bufferedWriter.write(iceCreamList_web_html_footer_string);
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

            String website_remoteFile = "icecream.html";
            InputStream website_inputStream = new FileInputStream(iceCreamList_htmlFile);
            boolean website_done = ftpClient.storeFile(website_remoteFile, website_inputStream);
            if (website_done) {
                System.out.println("beerList_website uploaded");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
