package addressbook.entity;

import addressbook.util.Constant;

/**
 * 联系人实体类
 * @author DubLBo
 */
public class ContactPerson {
    private String photo;    // 照片
    private String name;     // 姓名 (not null, primary key)
    private String phone;    // 电话号码 (not null)
    private String email;    // 邮箱地址 (not null)
    private String group;    // 所在组
    private String birthday; // 生日
    private String office;   // 工作单位
    private String address;  // 住址
    private String postcode; // 邮编
    private String remark;   //备注

    public ContactPerson() {}

    public ContactPerson(String source){
        String[] fields = source.split(Constant.FILE_SPLITTER);
        this.photo = Constant.FSTransNull(fields[Constant.PERSON_INFO.PHOTO.ordinal()]);
        this.name = Constant.FSTransNull(fields[Constant.PERSON_INFO.NAME.ordinal()]);
        this.phone = Constant.FSTransNull(fields[Constant.PERSON_INFO.PHONE.ordinal()]);
        this.email = Constant.FSTransNull(fields[Constant.PERSON_INFO.EMAIL.ordinal()]);
        this.group = Constant.FSTransNull(fields[Constant.PERSON_INFO.GROUP.ordinal()]);
        this.birthday = Constant.FSTransNull(fields[Constant.PERSON_INFO.BIRTHDAY.ordinal()]);
        this.office = Constant.FSTransNull(fields[Constant.PERSON_INFO.OFFICE.ordinal()]);
        this.address = Constant.FSTransNull(fields[Constant.PERSON_INFO.ADDRESS.ordinal()]);
        this.postcode = Constant.FSTransNull(fields[Constant.PERSON_INFO.POSTCODE.ordinal()]);
        this.remark = Constant.FSTransNull(fields[Constant.PERSON_INFO.REMARK.ordinal()]);
    }

    @Override
    public String toString() {
        return "ContactPerson{" +
                "photo='" + photo + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", group='" + group + '\'' +
                ", birthday='" + birthday + '\'' +
                ", office='" + office + '\'' +
                ", address='" + address + '\'' +
                ", postcode='" + postcode + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    /**
     * 将对象的属性解析成一个以制表符为间隔的字符串，便于存入文本文件
     * @return String
     */
    public String decode() {
        return String.join(Constant.FILE_SPLITTER,
                Constant.nullTransFS(photo),
                Constant.nullTransFS(name),
                Constant.nullTransFS(phone),
                Constant.nullTransFS(email),
                Constant.nullTransFS(group),
                Constant.nullTransFS(birthday),
                Constant.nullTransFS(office),
                Constant.nullTransFS(address),
                Constant.nullTransFS(postcode),
                Constant.nullTransFS(remark));
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
