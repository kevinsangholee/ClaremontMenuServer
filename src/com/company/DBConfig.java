package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kevinlee on 12/30/16.
 */

public class DBConfig {

    public static final int FRANK = 0;
    public static final int FRARY = 1;
    public static final int OLDENBORG = 2;
    public static final int CMC = 3;
    public static final int SCRIPPS = 4;
    public static final int PITZER = 5;
    public static final int MUDD = 6;

    public static final String URL_GET_FOOD = "https://claremontmenu.com/pdo/getFood.php?";
    public static final String URL_ADD_ASPC_FOOD = "https://claremontmenu.com/pdo/addASPCFood.php";
    public static final String URL_UPDATE_IMAGE = "https://claremontmenu.com/pdo/updateImage.php";


    public static final String KEY_JSON = "json";

    public static final String KEY_FOOD_ID = "id";
    public static final String KEY_FOOD_NAME = "name";
    public static final String KEY_FOOD_SCHOOL = "school";
    public static final String KEY_FOOD_IMAGE = "image";

    public static final String TAG_FOOD_ID = "id";
    public static final String TAG_FOOD_NAME = "name";
    public static final String TAG_FOOD_IMAGE = "image";

    public static final String ASPC_BASE_URL = "https://aspc.pomona.edu/api/menu/";
    public static final String ASPC_AUTH_TOKEN = "/?auth_token=2a9c963d749d6de4933579c611723625b74521c0";
    public static final String ASPC_DINING_HALL = "dining_hall";
    public static final String ASPC_DAY = "day";
    public static final String ASPC_FOOD_ITEMS = "food_items";

    public static final String TAG_BING_VALUE = "value";
    public static final String TAG_BING_CONTENT_URL = "contentUrl";

}
