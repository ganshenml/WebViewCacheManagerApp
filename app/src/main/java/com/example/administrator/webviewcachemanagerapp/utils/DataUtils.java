package com.example.administrator.webviewcachemanagerapp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/24.
 */

public class DataUtils {

    public static List<Map<String, String>> returnData() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("name", "网页1");
        map.put("id", "1001");
        list.add(map);

        Map<String, String> map2 = new HashMap<>();
        map2.put("name", "网页2");
        map2.put("id", "1002");
        list.add(map2);

        Map<String, String> map3 = new HashMap<>();
        map3.put("name", "网页3");
        map3.put("id", "1003");
        list.add(map3);

        return list;
    }

    public static String returnUrl(int position) {

        switch (position) {
            case 1:
                return "http://a.mp.uc.cn/article.html?uc_param_str=frdnsnpfvecpntnwprdssskt&client=ucweb&wm_aid=c51bcf6c1553481885da371a16e33dbe&wm_id=482efebe15ed4922a1f24dc42ab654e6&pagetype=share&btifl=100";
            case 2:
                return "http://a.mp.uc.cn/article.html?uc_param_str=frdnsnpfvecpntnwprdssskt&client=ucweb&wm_aid=81044770ad43495082aebf3943be5b71&wm_id=3a2778f417bc4f4f9997df3fe1a18e32&pagetype=share&btifl=100";
            case 3:
                return "http://a.mp.uc.cn/article.html?uc_param_str=frdnsnpfvecpntnwprdssskt&client=ucweb&wm_aid=00208d73fa07484a964de3f2c564a6e0&wm_id=4a3605e9dc6c440684ec577aeaf9cd30&pagetype=share&btifl=100";
            default:
                break;
        }
        return "";

    }

    public static String returnId(int position) {

        switch (position) {
            case 1:
                return "1001";
            case 2:
                return "1002";
            case 3:
                return "1003";
            default:
                break;
        }
        return "";

    }


}
