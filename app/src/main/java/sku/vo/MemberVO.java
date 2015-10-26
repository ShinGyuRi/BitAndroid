package sku.vo;

/**
 * Created by 618 on 2015-09-15.
 */
public class MemberVO {

    private String id;
    private String password;
    private String name;
    private String phone;
    private String post1;
    private String post2;
    private String address;
    private String detailAddress;
    private String email;


    public MemberVO() {
        super();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPost1() {
        return post1;
    }

    public void setPost1(String post1) {
        this.post1 = post1;
    }

    public String getPost2() {
        return post2;
    }

    public void setPost2(String post2) {
        this.post2 = post2;
    }
}
