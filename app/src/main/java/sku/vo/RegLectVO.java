package sku.vo;

/**
 * Created by 618 on 2015-10-30.
 */
public class RegLectVO {
    private int lect_num, reg_num, assign_num, price, status;
    private String book_name, lect_name, reg_date, beginTime, endTime, member_id, phone, email, post, address, detail_address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAssign_num() {
        return assign_num;
    }

    public void setAssign_num(int assign_num) {
        this.assign_num = assign_num;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLect_name() {
        return lect_name;
    }

    public void setLect_name(String lect_name) {
        this.lect_name = lect_name;
    }

    public int getLect_num() {
        return lect_num;
    }

    public void setLect_num(int lect_num) {
        this.lect_num = lect_num;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public int getReg_num() {
        return reg_num;
    }

    public void setReg_num(int reg_num) {
        this.reg_num = reg_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
