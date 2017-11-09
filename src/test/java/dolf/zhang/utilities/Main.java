package dolf.zhang.utilities;


import com.fasterxml.jackson.core.JsonGenerationException;
import dolf.zhang.utilities.json.JackSonUtilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author dolf
 * @description
 * @date 16/12/27
 */
public class Main {



    public static void main(String[] args) throws JsonGenerationException {

        List<User> list = new ArrayList<>();
        User user = new User();
        user.setId(1l);
        user.setData(new Date());
        list.add(user);
        List<User> list1 = JackSonUtilities.toList(JackSonUtilities.toString(list), User.class);

        for (User u : list1){
            System.out.println(u);
        }
    }

    static  class User {
        private long id ;

        private Date data ;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }


        public Date getData() {
            return data;
        }

        public void setData(Date data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", data=" + data +
                    '}';
        }
    }
}
