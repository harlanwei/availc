package Parser;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * @implNote The {@code Parser} class is implemented using Selenium's interface. Basically,
 * when a parser instance is created, the parser will try to initiate a new Chromium browser
 * instance and manipulate it. However, the Chromium instance must be explicitly closed.
 * Therefore, call {@code Parser::close} when your work with the parser is done.
 */
public class Parser implements Closeable {
    private WebDriver driver;

    /**
     * This constructor should only be used for tests.
     */
    private Parser() {
        this(false);
    }

    private Parser(boolean startWithChromium) {
        if (startWithChromium) {
            // Disable Chrome Driver's output
            System.setProperty("webdriver.chrome.silentOutput", "true");

            // Disable Selenium's output
            java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

            // Set Chrome Driver's location
            final String DEFAULT_CHROME_DRIVER_PATH = "C:\\Users\\Vian\\Downloads\\chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", DEFAULT_CHROME_DRIVER_PATH);

            // Start Chrome in the headless mode and disable all extensions to speed up the loading process
            ChromeOptions options = new ChromeOptions().addArguments(
                    "--headless", "--disable-gpu", "--disable-extensions", "-incognito"
            );

            this.driver = new ChromeDriver(options);
        }
    }

    public Parser(String username, String password) {
        // construct Chromium instance
        this(true);

        // Mimic a user logging in
        driver.get("https://e.buaa.edu.cn/users/sign_in");
        driver.findElement(By.id("user_login")).sendKeys(username);
        driver.findElement(By.id("user_password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"login-form\"]/div[3]/input")).click();


        // Do not act until login finished. the {@code timeOutInSeconds} in this call means that after
        // this many seconds later, if the expectation is still unfulfilled, then the function should
        // just throw a {@code TimeoutException}. The {@code sleepInMillis} indicates how often should
        // the expectation be checked. The default value is 500, but here it is changed to speed up the
        // whole process. Likewise for the {@code WebDriverWait} call below.
        new WebDriverWait(driver, 10, 200)
                .until(ExpectedConditions.urlToBe("https://e.buaa.edu.cn/"));

        // Get token to the platform
        driver.get("https://10-200-21-61-7001.e.buaa.edu.cn/ieas2.1/");
        driver.findElement(By.xpath("//*[@id=\"notice\"]/div[2]/div[1]/p[2]/input")).click();
        new WebDriverWait(driver, 10, 200)
                .until(ExpectedConditions.urlToBe("https://10-200-21-61-7001.e.buaa.edu.cn/ieas2.1/welcome"));
    }

    @Override
    public void close() {
        if (this.driver != null)
            driver.quit();
    }

    private void parse(Map<String, Boolean[]> map, String htmlString) {
        Document document = Jsoup.parse(htmlString);
        this.parse(map, document);
    }

    private void parse(Map<String, Boolean[]> map, File file) throws IOException {
        Document document = Jsoup.parse(file, "utf-8");
        this.parse(map, document);
    }

    /**
     * Parse a HTML from empty classroom query page.
     *
     * @implNote The page usually has a table, the first two rows of which are the headers, and proceeding rows
     * contains the information we really cares about. The first cell of each row is the classroom's name; all
     * the other cells indicates whether at a specific time it is available. Each cell should contain a {@code div}
     * element whose class containing {@code kjs_icon} tells it is free.
     */
    private void parse(@NotNull Map<String, Boolean[]> map, @NotNull Document document) {
        Elements table = document.select("body > div > div > div.list > table > tbody").first().children();
        List<Element> elList = Lists.newArrayList(table.iterator());

        for (int i = 2; i < elList.size(); ++i) {
            List<Element> currentRow = Lists.newArrayList(elList.get(i).children().iterator());
            Boolean[] available = new Boolean[42];
            for (int j = 1; j < currentRow.size(); ++j) {
                available[j - 1] = currentRow.get(j).child(0).hasClass("kjs_icon");
            }
            String classroomName = currentRow.get(0).text();
            map.put(classroomName, available);
        }
    }

    public static void main(String[] args) throws IOException {
        Map<String, Boolean[]> results = new HashMap<>();
        try (Parser p = new Parser()) {
            p.parse(results, new File("C:\\Users\\Vian\\Downloads\\test.html"));
            System.out.println(results.get("J4-101")[0]);
        }
    }
}
