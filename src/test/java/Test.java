import com.changhewl.hotel.command.CommandModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/23.
 */
public class Test {
    public static void main(String[] args) {
        //FC0017000120F41B90F22A01010400124B000725354B080020b8
        String ss="00 01 20 F4 1B 90 F2 2A 03 01 00 10 FF";
        CommandModel model=new CommandModel();
        model.setFlag("FC");//1
        model.setTp("0001");//2
        model.setMac("20F41B90F22A");//6
        model.setDirection("01");//1
        List<String> dataArray=new ArrayList<String>();
        dataArray.add("12");
        dataArray.add("02");
        //12 11
        //00, 12, 4B, 00, 07, 25, 35, 4B, 08, 00, 20
        String str="00, 12, 4B, 00, 07, 25, 35, 4B, 08";//11
        String [] infos=str.split(", ");
        for(String info:infos){
            dataArray.add(info);
        }
        model.setDataArray(dataArray);
       String order=  model.compentOrder(9+11);
       System.out.println(order);

    }
}
