package com.mingtai.mt.entity;

import java.io.Serializable;

/**
 * Created by LG on 2020/1/2.
 */
public class StoreInfoAddressBean implements Serializable {
  private String AddressID;
  private int UserId;
  private String Name;
  private String Address;
  private String Mobile;
  private String Phone;
  private String CreateDate;
  private boolean IsDefault;
  private int country;
  private int prov;
  private int area;
  private int city;
  private String province_name;
  private String city_name;
  private String area_name;
  private String town_name;
  private String UserName;
  private String Post;

  public StoreInfoAddressBean() {
  }

  public StoreInfoAddressBean(String addressID, int userId, String name, String address, String mobile, String phone, String createDate, boolean isDefault, int country, int prov, int area, int city, String province_name, String city_name, String area_name, String town_name, String userName, String post) {
    AddressID = addressID;
    UserId = userId;
    Name = name;
    Address = address;
    Mobile = mobile;
    Phone = phone;
    CreateDate = createDate;
    IsDefault = isDefault;
    this.country = country;
    this.prov = prov;
    this.area = area;
    this.city = city;
    this.province_name = province_name;
    this.city_name = city_name;
    this.area_name = area_name;
    this.town_name = town_name;
    UserName = userName;
    Post = post;
  }

  public String getAddressID() {
    return AddressID;
  }

  public void setAddressID(String addressID) {
    AddressID = addressID;
  }

  public int getUserId() {
    return UserId;
  }

  public void setUserId(int userId) {
    UserId = userId;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getAddress() {
    return Address;
  }

  public void setAddress(String address) {
    Address = address;
  }

  public String getMobile() {
    return Mobile;
  }

  public void setMobile(String mobile) {
    Mobile = mobile;
  }

  public String getPhone() {
    return Phone;
  }

  public void setPhone(String phone) {
    Phone = phone;
  }

  public String getCreateDate() {
    return CreateDate;
  }

  public void setCreateDate(String createDate) {
    CreateDate = createDate;
  }

  public boolean isDefault() {
    return IsDefault;
  }

  public void setDefault(boolean aDefault) {
    IsDefault = aDefault;
  }

  public int getCountry() {
    return country;
  }

  public void setCountry(int country) {
    this.country = country;
  }

  public int getProv() {
    return prov;
  }

  public void setProv(int prov) {
    this.prov = prov;
  }

  public int getArea() {
    return area;
  }

  public void setArea(int area) {
    this.area = area;
  }

  public int getCity() {
    return city;
  }

  public void setCity(int city) {
    this.city = city;
  }

  public String getProvince_name() {
    return province_name;
  }

  public void setProvince_name(String province_name) {
    this.province_name = province_name;
  }

  public String getCity_name() {
    return city_name;
  }

  public void setCity_name(String city_name) {
    this.city_name = city_name;
  }

  public String getArea_name() {
    return area_name;
  }

  public void setArea_name(String area_name) {
    this.area_name = area_name;
  }

  public String getTown_name() {
    return town_name;
  }

  public void setTown_name(String town_name) {
    this.town_name = town_name;
  }

  public String getUserName() {
    return UserName;
  }

  public void setUserName(String userName) {
    UserName = userName;
  }

  public String getPost() {
    return Post;
  }

  public void setPost(String post) {
    Post = post;
  }
}