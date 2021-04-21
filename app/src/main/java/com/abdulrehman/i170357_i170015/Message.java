package com.abdulrehman.i170357_i170015;

public class Message {
    private String sfirstName, slastName, message, rfirstName, rlastName;

    public Message(String sfirstName, String slastName, String message, String rfirstName, String rlastName) {
        this.sfirstName = sfirstName;
        this.slastName = slastName;
        this.message = message;
        this.rfirstName = rfirstName;
        this.rlastName = rlastName;
    }

    public String getSfirstName() { return sfirstName; }

    public void setSfirstName(String sfirstName) { this.sfirstName = sfirstName; }

    public String getSlastName() { return slastName; }

    public void setSlastName(String slastName) { this.slastName = slastName; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getRfirstName() { return rfirstName; }

    public void setRfirstName(String rfirstName) { this.rfirstName = rfirstName; }

    public String getRlastName() { return rlastName; }

    public void setRlastName(String rlastName) { this.rlastName = rlastName; }
}
