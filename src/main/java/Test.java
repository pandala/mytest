import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

  private static final String url = "https://api.huobipro.com/v1/order/orders/place";

  private static String amount = "0";

  private static String price = "1";

  private static String type = "sell-limit";

  private final static String source = "web";

  private final static String symbol = "gnxeth";

  private Long accountId;

  private String hbProToken;

  private OkHttpClient client = new OkHttpClient();

  public Test(Long accountId, String hbProToken) {
    this.accountId = accountId;
    this.hbProToken = hbProToken;
  }

  public static void setPrice(String price) {
    Test.price = price;
  }

  public static void setAmount(String amount) {
    Test.amount = amount;
  }

  public static void setType(String type) {
    Test.type = type;
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
    Date now = new Date();
    System.out.println("miaomiao\t" + accountId + ":\t"+ dateFormat.format(now));
    miaomiao();
    now = new Date();
    System.out.println(dateFormat.format(now));
  }


  public static void main(String[] args) throws IOException, InterruptedException {

    String type = args[0];
    Long accountId = Long.valueOf(args[1]);
    String token = args[2];
    String amount = args[3];
    String price = args[4];
    Long sleeps = Long.valueOf(args[5]);

    //控制触发时间的
    long controlMinute = Long.valueOf(args[6]);
    long count = Long.valueOf(args[7]);

    System.out.println("type:\t" + type);
    System.out.println("accountId:\t" + accountId);
    System.out.println("token:\t" + token);
    System.out.println("amount:\t" + amount);
    System.out.println("price:\t" + price);
    System.out.println("sleeps:\t" + sleeps);
    System.out.println("control minute:\t" + controlMinute);
    System.out.println("count:\t" + count);

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

        if (hours == 12 || hours == 9 || hours == 11 || hours == 13 || hours == 15 || hours == 17) {
          if (minutes == controlMinute && seconds == 59) {
            break;
          }
        }
        Thread.sleep(10);
      } catch (Throwable e) {

      }
    }

    Thread.sleep(sleeps);

    Date now = new Date();
    System.out.println("miaomiao\t" + ":\t"+ dateFormat.format(now));

    Test test = new Test(accountId, token);
    test.setPrice(price);
    test.setType(type);
    test.setAmount(amount);

    for (int i = 0; i < count; i++) {
      test.run();
    }
  }
}
