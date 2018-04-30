package me.zuhr.demo.server.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析异常
 *
 * @author zurun
 * @date 2018/3/19 00:27:44
 */
public class ExceptionUtil {
    /**
     * 获取Exception的详细信息
     * e.printStackTrace
     *
     * @param e
     * @return
     */
    public static String getExceptionDetail(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e2) {
            return "bad getErrorInfoFromException";
        }
    }


    /**
     * 出错点如何获得呢？出错点信息一般在“Cause by:”标识开始的行,通过正则表达式获取
     *
     * @param e
     * @return
     */
    public static String getCauseBy(Exception e) {
        String regEx = "Caused by:(.*)";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(getExceptionDetail(e));
        boolean rs = mat.find();
        String ret = "";
        if (rs) {
            ret += mat.group(0) + ";";
        } else {
            return null;
        }

        return ret;
    }
}
