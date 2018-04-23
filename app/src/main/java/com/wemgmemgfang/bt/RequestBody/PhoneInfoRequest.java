package com.wemgmemgfang.bt.RequestBody;

/**
 * sayid ....
 * Created by wengmf on 2018/4/8.
 */

public class PhoneInfoRequest {

    int isPhone; //  判断设备是否是手机  0 是手机   1 不是手机
    String PhoneNumber; // 手机号
    String IMEI; //  获取IMEI码
    String IMSI;  //  获取IMSI码
    int PhoneType; //  获取移动终端类型
    String IPAddress;    //  获取IP地址
    String   NetworkOperatorName; // 获取移动网络运营商名称
    String Locality ; // 获取所在地
    int SDKVersion; // 获取设备系统版本号
    String  AndroidID ; // 获取设备AndroidID
    String  MacAddress ; // 获取设备MAC地址
    String Manufacturer; // 获取设备厂商
    String  Model ; // 获取设备型号


    public int getIsPhone() {
        return isPhone;
    }

    public void setIsPhone(int isPhone) {
        this.isPhone = isPhone;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public int getPhoneType() {
        return PhoneType;
    }

    public void setPhoneType(int phoneType) {
        PhoneType = phoneType;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getNetworkOperatorName() {
        return NetworkOperatorName;
    }

    public void setNetworkOperatorName(String networkOperatorName) {
        NetworkOperatorName = networkOperatorName;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public int getSDKVersion() {
        return SDKVersion;
    }

    public void setSDKVersion(int SDKVersion) {
        this.SDKVersion = SDKVersion;
    }

    public String getAndroidID() {
        return AndroidID;
    }

    public void setAndroidID(String androidID) {
        AndroidID = androidID;
    }

    public String getMacAddress() {
        return MacAddress;
    }

    public void setMacAddress(String macAddress) {
        MacAddress = macAddress;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    @Override
    public String toString() {
        return "PhoneInfoRequest{" +
                "isPhone=" + isPhone +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", IMEI='" + IMEI + '\'' +
                ", IMSI='" + IMSI + '\'' +
                ", PhoneType='" + PhoneType + '\'' +
                ", IPAddress='" + IPAddress + '\'' +
                ", NetworkOperatorName='" + NetworkOperatorName + '\'' +
                ", Locality='" + Locality + '\'' +
                ", SDKVersion='" + SDKVersion + '\'' +
                ", AndroidID='" + AndroidID + '\'' +
                ", MacAddress='" + MacAddress + '\'' +
                ", Manufacturer='" + Manufacturer + '\'' +
                ", Model='" + Model + '\'' +
                '}';
    }
}
