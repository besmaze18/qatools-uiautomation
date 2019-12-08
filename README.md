# qatools-uiautomation

# Dependency Connection

To use Testkit library create maven-project. Than add last version as maven dependency:

```
<dependency>
      <groupId>qatools-uiautomation</groupId>
      <artifactId>QaToolsUiAutomation</artifactId>
      <version>1.0-SNAPSHOT</version>
</dependency>
```


Nest run mvn clean compile.

# Definition

This framework uses Yandex HtmlElements and allows you to develop tests without setting up additional logic. 
Framework has TestRail integration: 
The result and screenshot are saved using testrailid and testrunid.
For uploading screenshots we are using Yandex Drive and passing links to TestRail test case.
Fro testing framework we are using TestNG.

# Testkit using example

Let's use Yandex main page as example (http://www.yandex.com). 
You can see block of elements below:

```
@Name("Search Block")
@FindBy(xpath = "//form[contains(@class,'search2')]")
public class SearchBlock extends HtmlElement {

    @FindBy(xpath ="//div[@class='search2__input']//input[@class='input__control input__input']")
    public TextInput searchInput;

    @FindBy(xpath ="//div[@class='search2__button']/button")
    public TextInput searchButton;
}
```

This class contains search elements. 
Let's add yandex base page class YandexMainPage inherited from PageBase and contains search block:

```
public abstract class YandexMainPage extends PageBase{
    protected YandexMainPage(Browser browser) {
        super(browser);
    }

    public SearchBlock searchBlock;
}
```

All the page classes should be inherited from YandexMainPage.

See search page class below:

```
public class YandexSearchPage extends YandexMainPage {

    public YandexSearchPage(Browser browser) {
        super(browser);
        actions = new YandexSearchPageActions(this.browser, this);
    }

    public YandexSearchPageActions actions;

    /**
     * elements for page identification and page loading awaiter
     */
    public static final How HOW = XPATH;
    public static final String[] USING = {"//div[@class='home-arrow__tabs']",
            "//div[contains(@class,'container__heap container__line')]"};
}
```
Parameters HOW and USING identifies the page on calling isOnCurrentPage(How how, String[] using) method.
isOnCurrentPage awaits for elements availability using Javascript.
All the web pages actions are separated from page classes to page action classes.
YandexMainPageActions base page actions class:

```
public class YandexMainPageActions<T extends PageBase> implements PageActionsBase {

    protected T page;
    protected Browser browser;

    protected YandexMainPageActions(Browser browser, T page)
    {
        this.browser = browser;
        this.page = page;
    }
}
```

All the page actions classes should be inherited from base class:

```
public class YandexSearchPageActions extends YandexMainPageActions<YandexSearchPage> {
    public YandexSearchPageActions(Browser browser, YandexSearchPage yandexSearchPage) {
        super(browser, yandexSearchPage);
    }

    public YandexSearchResultPage searchByText(String expectedText){
        page.searchBlock.searchInput.sendKeys(expectedText);
        page.searchBlock.searchButton.click();
        YandexSearchResultPage yandexSearchResultPage = new YandexSearchResultPage(browser);
        yandexSearchResultPage.isOnCurrentPage(YandexSearchResultPage.HOW, YandexSearchResultPage.USING);
        return yandexSearchResultPage;
    }
}
```
Please see the test using created classes below:

```
public class YandexSearchDemoTest extends TestSuite {

    //get webdriver properties
    private static final WebDriverProperties webDriverProperties = new WebDriverProperties();
    private static final String baseUrl = webDriverProperties.getBaseUrl();
    
    @Test(retryAnalyzer = RetryAnalyzer.class, testName = "your TestRail testcase id")
    public void yandexSearchDemoTest(){
        browser.navigate(baseUrl);
        YandexSearchPage yandexSearchPage = new YandexSearchPage(browser);
        yandexSearchPage.isOnCurrentPage(YandexSearchPage.HOW, YandexSearchPage.USING);
        YandexSearchResultPage yandexSearchResultPage = yandexSearchPage.actions.searchByText("ожидаемый результат");
        yandexSearchResultPage.actions.checkSearchTopResult();
    }
}
```

For browser set up use webdriver.properties file

```
isRemote=boolean parameter where "true" value is to running your browser using RemoteWebdriver, "false" is to run browser locally 
driverAdditionalOptions=options to run your browser, for example "--start-maximized"
remoteUrl=remote virtual machine URL, for running tests on local computer - "http://localhost:9515"
browserType=Chrome, Firefox, Safari, IE
baseUrl=your app under test url without "http://", for example "yandex.ru"
login=login for authorization in your tested app
password=password for authorization in your tested app
screenshotPath=local filepath for screenshots
```
For TestRail integration use testrail.properties file: 

```
endPoint = TestRail URL, for example: https://sibur.testrail.io/
username = TestRail username
password = TestRail password
testrailRun = TestRail testrun id
```

For Yandex Drive integration use yandexdrive.properties file (for saving screenshots and attaching links to TestRail): 

```
baseUrl=https://cloud-api.yandex.net:443
accessToken=Authorization header value which first part is "OAuth" and second is accessToken from your Yandex Drive user
```


