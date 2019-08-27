# ui-automation-testkit

# Подключение зависимостей

Чтобы использовать библиотеку Testkit необходимо создать простой maven-проект. После этого добавить в зависимости свежую версию библиотеки:

```
<dependency>
      <groupId>uiautomation</groupId>
      <artifactId>UiAutomationTestkit</artifactId>
      <version>1.0-SNAPSHOT</version>
</dependency>
```


Выполните команду mvn clean compile, чтобы проверить, что ваш проект компилируется.

# Краткое описание

Данный фреймворк написан с использованием Yandex HtmlElements, но в дополнение, позволяет начинать писать тесты сразу, без описания логики взаимодействия с браузерами, test listener'ов и базового Test suite. 
Так же, реализована интеграция с TestRail: 
Запись результата выполненного теста и сохранения скриншота (в случае упавших тестов) в интерфейс TestRail (по testrun id и test case id).
Для сохранения скриншотов используется интеграция с YandexDrive (сохранеие скриншота на диске и передача ссылки на файл в TestRail).
В качестве тестового фреймворка используется TestNG.

# Пример использования Testkit

В качестве примера возьмем главную страницу Яндекса (http://www.yandex.ru). 
Для начала опишем простой блок элементов, например поисковую строку:

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

Этот класс описывает структуру поисковой строки. 
Дальше необходимо создать базовый класс YandexMainPage, который наследуется от PageBase и содержит поисковой блок:

```
public abstract class YandexMainPage extends PageBase{
    protected YandexMainPage(Browser browser) {
        super(browser);
    }

    public SearchBlock searchBlock;
}
```

Далее, все классы страниц проекта буду наследоваться от YandexMainPage.

Опишем поисковую страницу:

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
На странице отображаются параметры HOW и USING, данные параметры необходимы для идентификации страницы при вызове метода isOnCurrentPage(How how, String[] using).
Метод isOnCurrentPage вызывается при переходе со страницы на страницу, проверяет наличие заданных элементов и ожидает их отобображение используя Javascript.
Все действия с элементами страницы отображаются в отдельных классах (Page Actions).
Для этого создаем базовый класс YandexMainPageActions:

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

Далее, наследуемся от созданного класса:

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
Для того, чтобы проверить взаимодействие указанных выше классов создадим простенький тест для проверки поисковых результатов, в котором создим экземляр нашей страницы:

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

Для выбора браузера и способа его запуска и т.д. используется файл webdriver.properties

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
Задание параметров интеграции с TestRail testrail.properties файл: 

```
endPoint = TestRail URL, for example: https://sibur.testrail.io/
username = TestRail username
password = TestRail password
testrailRun = TestRail testrun id
```

Задание параметров интеграции с Yandex Drive yandexdrive.properties файл: 

```
baseUrl=https://cloud-api.yandex.net:443
accessToken=Authorization header value which first part is "OAuth" and second is accessToken from your Yandex Drive user
```


