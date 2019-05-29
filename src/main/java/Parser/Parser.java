package Parser;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

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
    private String username;
    private String password;
    private int pageSize;
    private Cache cache = new Cache();

    private static String getChromeDriverPath() {
        String osName = System.getProperty("os.name");
        if (Platform.extractFromSysProperty(osName).is(Platform.WINDOWS)) {
            return "/chromedriver.exe";
        }
        return "/chromedriver";
    }

    private static String getChromiumPath() {
        String osName = System.getProperty("os.name");
        Platform currentPlatform = Platform.extractFromSysProperty(osName);
        if (currentPlatform.is(Platform.WINDOWS))
            return "/chrome-win/chrome.exe";
        if (currentPlatform.is(Platform.MAC))
            return "/Chromium.app/Contents/MacOS/Chromium";

        // Fall back to Linux
        return "/chrome-linux/chrome";
    }

    /**
     * Start a Chromium browser (optionally in headless mode).
     *
     * @param headless Starting Chromium in headless mode means that the browser interface will be
     *                 hidden to the end user. Running non-headlessly would be quite a fun, though.
     * @throws IllegalStateException When the {@code Parser} instance needs to go online to retrieve
     *                               more information but no username or password is provided.
     */
    private void startChromium(boolean headless) {
        final String DEFAULT_CHROME_DRIVER_PATH = System.getProperty("user.dir") + getChromeDriverPath();
        final String DEFAULT_CHROME_BINARY_PATH = System.getProperty("user.dir") + getChromiumPath();

        if (this.driver != null) return;
        if (this.username == null || this.password == null)
            throw new IllegalStateException("No username or password provided");

        // Disable Chrome Driver's output
        System.setProperty("webdriver.chrome.silentOutput", "true");

        // Disable Selenium's output
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);


        System.setProperty("webdriver.chrome.driver", DEFAULT_CHROME_DRIVER_PATH);

        // Start Chrome in the headless mode and disable all extensions to speed up the loading process
        ChromeOptions options = new ChromeOptions().addArguments(
                "--disable-gpu", "--disable-extensions", "-incognito"
        );

        if (headless) options.addArguments("--headless");

        options.setBinary(DEFAULT_CHROME_BINARY_PATH);

        this.driver = new ChromeDriver(options);

        login();
    }

    /**
     * A Parser that only reads disk caches.
     */
    public Parser() {
    }

    /**
     * A parser that might go online to retrieve more information.
     */
    public Parser(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Login with the provided username and password by mimicking user actions.
     */
    private void login() {
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
            // Close the Chromium instance.
            driver.quit();

        // Write caches onto the disk.
        try {
            this.cache.writeToDefaultLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param classrooms A nullable set of rooms that needs to be queried.
     * @return A map whose keys are the rooms' names, and the values are boolean arrays
     * containing 42 values each representing a range.
     */
    public Map<String, Boolean[]> isAvailable(Set<String> classrooms) {
        int currentWeek = getCurrentWeek();
        return isAvailable(classrooms, currentWeek, currentWeek);
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
    public Map<String, Boolean[]> isAvailable(Set<String> classrooms, int start, int end) {
        Map<String, Boolean[]> results = new HashMap<>();
        if (classrooms == null) return results;

        if (start < 1 || start > 18 || end < 1 || end > 18)
            throw new IllegalArgumentException(
                    "The time arguments are out of the predefined range."
            );

        if (start > end)
            throw new IllegalArgumentException("`start` can't be later than `end`.");

        Params params = Params.getAll();
        this.pageSize = params.pageSize;
        final Map<String, Room> rooms = params.rooms;

        classrooms
                .stream()
                .map(String::toLowerCase)
                .map(rooms::get)
                .sorted()
                .forEach(room -> results.put(room.name, query(room, start, end)));

        return results;
    }

    /**
     * Get all the rooms in a building. It's weird to have this method in this class though,
     * but the authors have decided not to expose the {@code Params} class to outside users.
     *
     * @param building The name of the building. The available options are: x1 (Xueyuanlu 1),
     *                 x3 (Xueyuanlu 3), x4 (Xueyuanlu 4), x7 (Xueyuanlu Main Building), x8 (
     *                 Xueyuanlu New Main Building), s1 (Shahe 1), s3 (Shahe 3), s4 (Shahe 5),
     *                 and s5 (Shahe 5). Case incensitive.
     * @return A set of strings each represents a room, which can be passed directly to the
     * {@code isAvailable} method.
     */
    public Set<String> getRoomsInTheBuilding(String building) {
        Params params = Params.getAll();
        Set<String> result = new HashSet<>();
        params.pages
                .keySet()
                .stream()
                .filter(el -> el.startsWith(building.toLowerCase()))
                .map(Params::getRoomsInPage)
                .forEach(
                        list -> result.addAll(list
                                .stream()
                                .map(el -> el.name)
                                .collect(Collectors.toList())
                        )
                );
        return result;
    }

    /**
     * The {@code query} method first checks if the information needed is stored on the disk. If so, it will just return
     * the information stored in the cache. Otherwise, try to parse the web page, and store the results into the cache.
     */
    private Boolean[] query(@NotNull Room room, int start, int end) {
        Boolean[] result = cache.getCached(room.name, start, end);
        if (result != null) return result;

        Map<String, Boolean[]> results = parseAll(room.page, start, end);
        for (String key : results.keySet()) {
            cache.add(key, start, end, results.get(key));
        }
        return results.get(room.name);
    }

    private Map<String, Boolean[]> parseAll(String page, int start, int end) {
        Page params = Params.getPageParams(page);
        List<Room> roomsToQuery = Params.getRoomsInPage(page);

        this.startChromium(false);

        // The empty classroom page is somehow required to be retrieved with a `POST` request. While Selenium does
        // not offer such interfaces, we, however, do have the access to the browser's console. Here we just send
        // a `POST` request with JavaScript.
        String postParams = String.format(
                "pageNo=%d&pageSize=%d&pageCount=%d&pageXnxq=%s&pageZc1=%d&pageZc2=%d&pageXiaoqu=%d&pageLhdm=%s&pageCddm=",
                params.pageNo,
                this.pageSize,
                params.pageCount,
                "2018-20192",
                start, end,
                params.pageXiaoqu,
                params.pageLhdm
        );

        // When async the `POST` request is finished, we execute `alert`, which in turn serves as a notification
        // mechanism telling Selenium that the script execution is finished.
        String script = String.format(
                "var xhr=new XMLHttpRequest();xhr.open('POST','https://10-200-21-61-7001.e.buaa.edu.cn/ieas2.1/kjscx/queryKjs/',true);xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');xhr.onload=function(){document.getElementsByTagName('body')[0].innerHTML=xhr.responseText;alert(' ')};xhr.send('%s')",
                postParams
        );

        JavascriptExecutor console = (JavascriptExecutor) driver;
        console.executeScript(script);
        new WebDriverWait(driver, 5, 200).until(ExpectedConditions.alertIsPresent());

        // The `alert` window will not be closed automatically by Selenium, so we have to do it ourselves.
        driver.switchTo().alert().dismiss();

        Document document = Jsoup.parse(driver.getPageSource());
        return parse(roomsToQuery, document);
    }

    // todo
    private int getCurrentWeek() {
        return 14;
    }

    /**
     * Parse the HTML of the empty classroom query page.
     *
     * @implNote The page usually has a table, the first two rows of which are the headers, and proceeding rows
     * contains the information we really cares about. The first cell of each row is the classroom's name; all
     * the other cells indicates whether at a specific time it is available. Each cell should contain a {@code div}
     * element whose class containing {@code kjs_icon} tells it is free.
     */
    private Map<String, Boolean[]> parse(@NotNull List<Room> rooms, @NotNull Document document) {
        Elements table = document
                .select("body > div > div > div.list > table > tbody")
                .first()
                .children();

        // Convert `Elements` to a `List<Element>` so that it will be easier to ignore the
        // first two rows.
        List<Element> rows = Lists.newArrayList(table);

        Map<String, Boolean[]> result = new HashMap<>();
        for (Room room : rooms) {
            result.put(room.name, parseRow(room.index, rows));
        }
        return result;
    }

    /**
     * Given all the rows and the index of the a classroom, parse the specific row to get
     * the availability of the classroom this week.
     */
    private Boolean[] parseRow(int rowIndex, @NotNull List<Element> rows) {
        // Add 2 because the first two rows are useless.
        List<Element> currentRow = Lists.newArrayList(rows.get(rowIndex + 2).children());
        return parseRow(currentRow);
    }

    /**
     * Given a row, parse this row to get all the information available about this classroom
     * this week.
     */
    private Boolean[] parseRow(@NotNull List<Element> row) {
        // 6 ranges: [1, 2], [3, 4, 5], [6, 7], [8, 9, 10], [11, 12], [13, 14] and 7 days
        final int ROW_CAPACITY = 6 * 7;

        Boolean[] result = new Boolean[ROW_CAPACITY];
        for (int i = 1; i < row.size(); ++i) {
            result[i - 1] = row.get(i).child(0).hasClass("kjs_icon");
        }
        return result;
    }

    /**
     * Used as a temporary test method. DO NOT CALL THIS METHOD.
     */
    public static void main(String[] args) {
        Set<String> s = new HashSet<>();
        s.add("j4-101");
        System.out.println("Logging in as " + args[0]);
        try (Parser p = new Parser(args[0], args[1])) {
            System.out.println(
                    "J4-101 is free on Monday [1, 2] this week: " + p.isAvailable(s, 1, 2).get("j4-101")[0]
            );
        }
    }
}
