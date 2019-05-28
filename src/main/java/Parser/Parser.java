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
import java.util.Set;
import java.util.logging.Level;

/**
 * DO NOT construct multiple parsers at the same time. See {@code implNote} for why.
 *
 * @implNote The {@code Parser} class is implemented using Selenium's interface. Basically,
 * when a parser instance is created, the parser will try to initiate a new Chromium browser
 * instance and manipulate it. However, the Chromium instance must be explicitly closed.
 * Therefore, call {@code Parser::close} when your work with the parser is done.
 */
public class Parser implements Closeable {
    private WebDriver driver;

    private Cache cache = new Cache();

    /**
     * This constructor should only be used for tests.
     */
    Parser(boolean startWithChromium) {
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

    public Parser(@NotNull String username, @NotNull String password) {
        // construct Chromium instance
        this(true);

        // ======================= Mimic a user logging in =======================
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


        // ====================== Get token to the platform ======================
        driver.get("https://10-200-21-61-7001.e.buaa.edu.cn/ieas2.1/");

        // Click on the login button
        driver.findElement(By.xpath("//*[@id=\"notice\"]/div[2]/div[1]/p[2]/input")).click();

        // Wait for it to finish loading
        new WebDriverWait(driver, 10, 200)
                .until(ExpectedConditions.urlToBe("https://10-200-21-61-7001.e.buaa.edu.cn/ieas2.1/welcome"));
    }

    @Override
    public void close() {
        if (this.driver != null)
            driver.quit();
    }

    void parse(Map<String, Boolean[]> map, String htmlString) {
        Document document = Jsoup.parse(htmlString);
        this.parse(map, document);
    }

    void parse(Map<String, Boolean[]> map, File file) throws IOException {
        Document document = Jsoup.parse(file, "utf-8");
        this.parse(map, document);
    }

    /**
     * @param classrooms A nullable set of rooms that needs to be queried.
     * @return A map whose keys are the rooms' names, and the values are boolean arrays
     * containing 42 values each representing a range.
     */
    public Map<String, Boolean[]> isAvailable(Set<String> classrooms) {
        int currentWeek = getCurrentWeek();
        return isAvaiable(classrooms, currentWeek, currentWeek);
    }

    /**
     * @param classrooms A nullable set of rooms that needs to be queried.
     * @param start      The starting week of the query in the range of [1, 18].
     * @param end        The ending week of the query in the range of [1, 18].
     * @return A map whose keys are the rooms' names, and the values are boolean arrays
     * containing 42 values each representing a range.
     * @throws IllegalArgumentException When {@code start} or {@code end} is out of the
     *                                  range of [1, 18]; or when {@code start > end}.
     */
    public Map<String, Boolean[]> isAvaiable(Set<String> classrooms, int start, int end) {
        Map<String, Boolean[]> results = new HashMap<>();
        if (classrooms == null) return results;

        if (start < 1 || start > 18 || end < 1 || end > 18)
            throw new IllegalArgumentException(
                    "The time arguments are out of the predefined range."
            );

        if (start > end)
            throw new IllegalArgumentException("`start` can't be later than `end`.");

        Params params = Params.getAll();

        // The {@code pageSize} parameter is usually useless, but for the purpose
        // of extensibility, we still place it here.
        final int pageSize = params.pageSize;

        final Map<String, Row> rooms = params.rooms;

        classrooms
                .stream()
                .map(rooms::get)
                .sorted()
                .forEach(row -> results.put(row.name, query(row, start, end)));

        return results;
    }

    // todo
    private Boolean[] query(@NotNull Row row, int start, int end) {
        Boolean[] result = cache.getCached(row.name, start, end);
        if (result != null) return result;

        Map<String, Boolean[]> results = parseAll(row.queryParams);
        for (String key : results.keySet()) {
            cache.addToCache(key, start, end, results.get(key));
        }
        return results.get(row.name);
    }

    private Map<String, Boolean[]> parseAll(RoomQueryParams queryParams) {
        Map<String, Boolean[]> parseResults = new HashMap<>();



        return parseResults;
    }

    // todo
    private int getCurrentWeek() {
        return 0;
    }

    /**
     * Parse the HTML of the empty classroom query page.
     *
     * @implNote The page usually has a table, the first two rows of which are the headers, and proceeding rows
     * contains the information we really cares about. The first cell of each row is the classroom's name; all
     * the other cells indicates whether at a specific time it is available. Each cell should contain a {@code div}
     * element whose class containing {@code kjs_icon} tells it is free.
     */
    private void parse(@NotNull Map<String, Boolean[]> map, @NotNull Document document) {
        Elements table = document
                .select("body > div > div > div.list > table > tbody")
                .first()
                .children();

        // Convert `Elements` to a `List<Element>` so that it will be easier to ignore the
        // first two rows.
        List<Element> rows = Lists.newArrayList(table.iterator());

        // todo
    }

    private Boolean[] parseRow(@NotNull List<Element> row) {
        // 6 ranges: [1, 2], [3, 4, 5], [6, 7], [8, 9, 10], [11, 12], [13, 14] and 7 days
        final int ROW_CAPACITY = 6 * 7;

        Boolean[] result = new Boolean[ROW_CAPACITY];
        for (int i = 1; i < row.size(); ++i) {
            result[i - 1] = row.get(i).child(0).hasClass("kjs_icon");
        }
        return result;
    }

    private Boolean[] parseRow(int rowIndex, @NotNull List<Element> rows) {
        // Add 2 because the first two rows are useless.
        List<Element> currentRow = Lists.newArrayList(rows.get(rowIndex + 2).children().iterator());
        return parseRow(currentRow);
    }
}
