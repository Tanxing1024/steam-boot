package com.stupidzhe.api.test;


import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * Created by Mr.W on 2017/6/27.
 */
public class RequestTest {


    public RequestTest(String words) {
        System.out.println(words);
    }

    public static void main(String... args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class re = Class.forName("com.stupidzhe.api.test.RequestTest2");
        System.out.println(re.toString());
        System.out.println(RequestTest.class.newInstance().toString());

        System.out.println(Modifier.toString(RequestTest.class.getModifiers()) + " " + RequestTest.class.getName() + " {");
        Constructor[] constructors = RequestTest.class.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.print("      " + constructor.getName() + "(");
            for (Class parameter : constructor.getParameterTypes()) {
                System.out.print(parameter.getName());
            }
            System.out.println(")");
        }
        System.out.println("}");
    }

    static {
        System.out.println("hello");
    }

    public static void test() throws IOException {
//        String url = "https://steamcommunity.com";
//        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
//        GetMethod method = new GetMethod(url);
//        httpClient.executeMethod(method);
//        System.out.println(method.getResponseBodyAsString());
    }
//    private static String getContent(InputStream inputStream) {
//        InputStreamReader inputStreamReader;
//        try {
//            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return null;
//        }
//        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//        String buff;
//        StringBuilder content = new StringBuilder();
//        try {
//            while (null != (buff = bufferedReader.readLine())) {
//                content.append(buff);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return content.toString();
//    }
}
class RequestTest2 {
    static {
        System.out.println("hello2");
    }
}
