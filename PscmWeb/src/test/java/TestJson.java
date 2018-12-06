import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestJson {
    public static void main(String[] args){
        String string = "{'laborInOutList':[{'inId':'1542676429422','examScore':90,'inDate':''},{'inId':'1542763444642','examScore':55,'inDate':''}]}";

        JSONObject jsonObj = JSONObject.fromObject(string);
        JSONArray jsonArray = (JSONArray) jsonObj.get("laborInOutList");//[{'inId':'1542676429422','examScore':90,'inDate':''},{'inId':'1542763444642','examScore':55,'inDate':''}]
        System.out.println(jsonArray);
        for (int i=0; i < jsonArray.size(); i++)    {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String inId = jsonObject.getString("inId");
            String examScore = jsonObject.getString("examScore");
            String inDate = jsonObject.getString("inDate");
            System.out.println("inId = "+inId+"===========examScore = "+examScore+"=========inDate = "+inDate);
        }


    }
}
