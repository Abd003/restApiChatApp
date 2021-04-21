package com.abdulrehman.i170357_i170015;

import android.provider.BaseColumns;

public class MyUserContract {
    private MyUserContract(){}
    public static int DB_VERSION=1;
    public static String DB_NAME="myChat.db";

    public static class Users implements BaseColumns {
        public static String TABLENAME="users";
        public static String _EMAIL="email";
        public static String _PASSWORD="password";
    }
    public static class Contacts implements BaseColumns {
        public static String TABLENAME="contacts";
        public static String _FIRSTNAME="firstName";
        public static String _LASTNAME="lastName";
        public static String _DATE="date";
        public static String _GENDER="gender";
        public static String _BIO="bio";
    }
    public static class Messages implements BaseColumns {
        public static String TABLENAME="messages";
        public static String _SENDERFIRSTNAME="sfirstName";
        public static String _SENDERLASTNAME="slastName";
        public static String _MESSAGE="date";
        public static String _RECEIVERFIRSTNAME="rfirstName";
        public static String _RECEIVERLASTNAME="rlastName";
    }
}