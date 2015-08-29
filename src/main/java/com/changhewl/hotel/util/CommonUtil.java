package com.changhewl.hotel.util;

import java.util.List;

/**
 * Created by Administrator on 2015/5/19 0019.
 */
public class CommonUtil {
    public static String join(List<String> list, String split)
    {
        StringBuffer sb = new StringBuffer();
        if ((list != null) && (list.size() > 0)) {
            for (int i = 0; i < list.size(); ++i) {
                if (i == list.size() - 1)
                    sb.append((String)list.get(i));
                else {
                    sb.append((String)list.get(i) + split);
                }
            }
        }
        return sb.toString();
    }
}
