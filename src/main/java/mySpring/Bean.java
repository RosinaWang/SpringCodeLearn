package mySpring;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean类，存放一个Bean拥有的属性
 * Created by Dell on 2017-08-13.
 */
public class Bean {
    private String id;
    private String type;
    private Map<String,Object> properties=new HashMap<String,Object>();
}
