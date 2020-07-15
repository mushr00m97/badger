package com.mushr00m.model;

public class LogoAddress {
    private String address;

    private String UploadDefaultAddress;

    public String getUploadDefaultAddress() {
        return UploadDefaultAddress;
    }

    public void setUploadDefaultAddress(String uploadDefaultAddress) {
        UploadDefaultAddress = uploadDefaultAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
