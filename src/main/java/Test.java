import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.github.tsohr.JSONObject;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Test implements Runnable {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

  private static final MediaType APPLICATION_JSON = MediaType.parse("application/json");

  private static final String url = "";

  private static String amount = "0";

  private static String price = "1";

  private String type = "sell-limit";

  private final static String source = "web";

  private final static String symbol = "mdseth";

  private Long sleep;

  private Long accountId;

  private String hbProToken;

  public final static Long dashuId = 100149L;
  public final static Long yatouId = 1221256L;
  public final static Long geId = 1221256L;
  public final static Long diId = 1221256L;
  public final static Long y1Id = 1221256L;
  public final static Long y2Id = 1221256L;


  private OkHttpClient client = new OkHttpClient();

  public static ThreadPoolExecutor THREADPOOL_EXECUTOR =
      (ThreadPoolExecutor) Executors.newFixedThreadPool(6);

  static private Map<Long, String> tokenMap = new HashMap<>();

  static {
    // 大叔
    tokenMap.put(100149L, "JO3Qbhh_zhsKeK-MPhbD-i16GbtS36WTS8qL2ZAZxWwY-uOP2m0-gvjE57ad1qDF");

    // 丫头
    tokenMap.put(1221256L, "k4zUzgqr05s9Q2rpd1eRGx402nEAYluJOa2blRvprBEY-uOP2m0-gvjE57ad1qDF");

    // 丫1
    tokenMap.put(1244341L, "u7O_WKrdUWOwrc75Zsy1AbasRjFrczd_JGJOSxBaydIY-uOP2m0-gvjE57ad1qDF");

    // 丫2
    tokenMap.put(1244359L, "R1OXEYW994N9-y_qbhsnUaJ1KHAGlG8sDC1mhLHQHI0Y-uOP2m0-gvjE57ad1qDF");

    // 哥
    tokenMap.put(1221256L, "k4zUzgqr05s9Q2rpd1eRGx402nEAYluJOa2blRvprBEY-uOP2m0-gvjE57ad1qDF");

    // 弟
    tokenMap.put(1221256L, "k4zUzgqr05s9Q2rpd1eRGx402nEAYluJOa2blRvprBEY-uOP2m0-gvjE57ad1qDF");
  }

  public Test(Long accountId, String hbProToken, String type, String amount, Long sleep) {
    this.accountId = accountId;
    this.hbProToken = hbProToken;
    this.type = type;
    this.amount = amount;
    this.sleep = sleep;
  }

  private void miaomiao() {
    String origin = "https://www.huobipro.com";

    String acceptEncoding = "gzip, deflate, br";

    String acceptLanguage = "zh-CN";

    String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36";

    String contentType = "application/json;charset=UTF-8";

    String accept = "application/json, text/plain, */*";

    String referer = "https://www.huobipro.com/zh-cn/gnx_eth/exchange/";

    String authority = "api.huobipro.com";

    Headers.Builder headerBuild = new Headers.Builder();
    headerBuild.add("origin", origin);
    headerBuild.add("accept-encoding", acceptEncoding);
    headerBuild.add("accept-language", acceptLanguage);
    headerBuild.add("user-agent", userAgent);
    headerBuild.add("content-type", contentType);
    headerBuild.add("accept", accept);
    headerBuild.add("referer", referer);
    headerBuild.add("authority", authority);
    headerBuild.add("hb-pro-token", hbProToken);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("amount", amount);
    if (type.equals("sell-limit")) {
      jsonObject.put("price", price);
    }
    jsonObject.put("type", type);
    jsonObject.put("source", source);
    jsonObject.put("symbol", symbol);
    jsonObject.put("account-id", accountId);

    RequestBody requestBody =  RequestBody.create(APPLICATION_JSON, jsonObject.toString());

    Request request = new Request.Builder().headers(headerBuild.build()).url(url).post(requestBody).build();

    try {
      Response response = client.newCall(request).execute();
      String s = response.body().string();
      System.out.println(s);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {

    try {
      Thread.sleep(sleep);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Date now = new Date();
    System.out.println("miaomiao\t" + accountId + ":\t"+ dateFormat.format(now));
    miaomiao();
    now = new Date();
    System.out.println(dateFormat.format(now));
  }

  public static void main(String[] args) throws IOException, InterruptedException {

    //控制测试启动的时、分
    long controlHour = Long.valueOf(args[0]);
    long controlMinute = Long.valueOf(args[1]);

    //延迟启动时间
    Long sleeps = Long.valueOf(args[2]);

    Calendar calendar;
    int hours;
    int minutes;
    int seconds;

    while(true) {
      try {
        calendar = Calendar.getInstance();
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        seconds = calendar.get(Calendar.SECOND);

        if (hours == controlHour || hours == 9 || hours == 11 || hours == 13 || hours == 15 || hours == 17) {
          if (minutes == controlMinute && seconds == 57) {
            break;
          }
        }
        Thread.sleep(10);
      } catch (Throwable e) {

      }
    }

    Thread.sleep(sleeps);

    Date now = new Date();
    System.out.println("begin add task:\t"+ dateFormat.format(now) + "-------------------------");

    Long start = 0L;
    maker(Test.dashuId, start);
    maker(Test.yatouId, start + 3L);
    maker(Test.y1Id, start + 6L);
    //maker(Test.diId, start + 9L);
   // maker(Test.geId, start + 12L);
    maker(Test.y2Id, start + 15L);

    taker(Test.dashuId, start + 5L);
    taker(Test.yatouId, start + 8L);
    taker(Test.y1Id, start + 11L);
    //taker(Test.diId, start + 14L);
    //taker(Test.geId, start + 17L);
    taker(Test.y2Id, start + 20L);

    // 第二次下单
    maker(Test.dashuId, start + 21);
    maker(Test.yatouId, start + 24L);
    maker(Test.y1Id, start + 27L);
    //maker(Test.diId, start + 30L);
    //maker(Test.geId, start + 33L);
    maker(Test.y2Id, start + 36L);

    taker(Test.dashuId, start + 23L);
    taker(Test.yatouId, start + 26L);
    taker(Test.y1Id, start + 29L);
    //taker(Test.diId, start + 32L);
    //taker(Test.geId, start + 35L);
    taker(Test.y2Id, start + 38L);

    // 第三次下单
    maker(Test.dashuId, start + 39);
    maker(Test.yatouId, start + 42L);
    maker(Test.y1Id, start + 45L);
    //maker(Test.diId, start + 48L);
    //maker(Test.geId, start + 51L);
    maker(Test.y2Id, start + 54L);

    taker(Test.dashuId, start + 41L);
    taker(Test.yatouId, start + 44L);
    taker(Test.y1Id, start + 47L);
    //taker(Test.diId, start + 50L);
    //taker(Test.geId, start + 53L);
    taker(Test.y2Id, start + 56L);
  }

  static private void maker(Long accountId, Long sleep) {
    String token = tokenMap.get(accountId);

    Test test = new Test(accountId, token, "sell-market", "11", sleep);
    THREADPOOL_EXECUTOR.execute(test);
  }

  static private void taker(Long accountId, Long sleep) {
    String token = tokenMap.get(accountId);

    Test test = new Test(accountId, token, "buy-market", "0.001", sleep);
    THREADPOOL_EXECUTOR.execute(test);
  }
}
