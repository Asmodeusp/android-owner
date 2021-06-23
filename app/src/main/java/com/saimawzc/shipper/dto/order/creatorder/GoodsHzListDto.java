package com.saimawzc.shipper.dto.order.creatorder;

import java.io.Serializable;
import java.util.List;

/****
 * 发货商 收货商
 * */

public class GoodsHzListDto implements Serializable {

   private List<data>list;
   private boolean isLastPage;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<data> getList() {
        return list;
    }

    public void setList(List<data> list) {
        this.list = list;
    }

    public class  data implements Serializable{
        private String id;
        private String userName;
        private String userAccount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }
    }
}
