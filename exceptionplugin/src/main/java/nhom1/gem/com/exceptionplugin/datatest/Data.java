package nhom1.gem.com.exceptionplugin.datatest;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nhom1.gem.com.exceptionplugin.network.dto.FeedbackDTO;

/**
 * Created by phuongtd on 14/03/2016.
 */
public class Data {

    public static String[] companyName = {"GEM Viet Nam", "FPT", "Viettel"};

    public static String[] companyId = {"046b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "056b6c7f-0b8a-43b9-b35d-6489e6daee91" ,
            "066b6c7f-0b8a-43b9-b35d-6489e6daee91"};

    public static String[] userName = {"Kathryn S. Waddell",
    "Michael C. Charlebois",
    "Annette G. Sparks",
    "Mia McDonald",
    "Spencer Sykes",
    "Jack Stephenson",
    "Aimee Whittaker",
    "Aaron Potts",
    "Nicholas Atkins",
    "Erin Pearson",
    "Nguyễn Văn Hợp",
    "Trần Duy Phương",
    "Cấn Văn Nghị",
    "Đặng Aanh Quân",
    "Summer Hancock"};

    public static String[] userId = {"001b6c7f-0b8a-43b9-b35d-6489e6daee91" ,
    "002b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "003b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "004b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "005b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "006b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "007b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "008b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "009b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "010b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "011b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "012b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "013b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "014b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "015b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "016b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "017b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "018b6c7f-0b8a-43b9-b35d-6489e6daee91",
    "019b6c7f-0b8a-43b9-b35d-6489e6daee91"};


    public static String[] avatars = {
            "avatar01.png" ,
            "avatar02.png" ,
            "avatar03.png" ,
            "avatar04.png" ,
            "avatar05.png" ,
            "avatar06.png" ,
            "avatar07.png",
            "avatar08.png",
            "avatar09.png",
            "default.png",
            "default.png",
            "default.png",
            "default.png",
            "default.png",
            "default.png",
            "default.png"
    };

    public static List<FeedbackDTO.UserInfo> listUserInfo(){

        List<FeedbackDTO.UserInfo> userInfos = new ArrayList<>();
        Random rand = new Random();

        for(int  i = 0 ; i < userName.length ; i ++){
            FeedbackDTO.UserInfo userInfo = new FeedbackDTO.UserInfo();
            userInfo.setUsername(userName[i]);
            userInfo.setAvatar(avatars[i]);

            userInfo.setUserId(UUIDDemo.getUUID());

            int  n = rand.nextInt(companyName.length - 1);

            userInfo.setCompanyName(companyName[n]);
            userInfo.setCompanyId(companyId[n]);

            userInfos.add(userInfo);
        }

        return userInfos;
    }


}
