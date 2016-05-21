package com.eucledian.comapp;


import com.eucledian.comapp.dao.AppUserDataSource;
import com.eucledian.comapp.dao.TokenDataSource;

public class Config {

    public static final class Database {
        public static final String NAME = "rewards.db";
        public static final int VERSION = 1;
        public static final String[] SQL_CREATE_STMT = {
                TokenDataSource.CREATE_TABLE,
                AppUserDataSource.CREATE_TABLE
        };
        public static final String[] SQL_DELETE_STMT = {
                TokenDataSource.DROP_TABLE,
                AppUserDataSource.DROP_TABLE
        };
    }
    public static final class Location{
        // Accuracy Calculations
        public static final int SIGNIFICANT_TIME = 1000 * 60 * 2;
        public static final int ACCURACY_DELTA = 200;
        /*
         * For location update request
         * */
        public static final int MILLIS_PER_SEC = 1000;
        public static final int SECONDS = 5;
        public static final int UPDATE_INTERVAL = MILLIS_PER_SEC * SECONDS;
        public static final int FAST_SECONDS = 1;
        public static final int FAST_INTERVAL = MILLIS_PER_SEC * FAST_SECONDS;
    }
    public static final class Transaction{
        public static final long REFRESH_INTERVAL = 10;
    }
    public static final class Camera{
        public static final String DIR = LOG_TAG;
        public static final int MAX_SIZE = 500;
    }
    public static final class Server {
        public static final int COMPRESSION = 60; // Image Compression 0 - 100
        // Local
        //public static final String ENDPOINT = "http://192.168.1.106";
        public static final String ENDPOINT = "http://10.49.65.19";
        public static final String PORT = "3000";
        public static final String SCOPE = "";
        // Production
        /**public static final String ENDPOINT = "http://tecnologias.csf.itesm.mx";
         public static final String PORT = "80";
         public static final String SCOPE = "/~Beacons/web";*/
        /**public static final String ENDPOINT = "http://dev-rewards-server.clicash.net";
         public static final String PORT = "81";
         public static final String SCOPE = "/web";*/
        // Global
        public static final String CONTROLLER = "/api";
        public static final String IMAGE = "/media";
        public static final class Urls{
            public static final class Users{
                public static final String LOGIN = "/users/login.json";
                public static final String SIGNUP = "/users/signup.json";
                public static final String ME = "/users/me.json";
            }
            public static final class Locations{
                public static final String LIST = "/locations.json";
                public static final String NEAR = "/locations/near.json";
            }
            public static final class Cards{
                public static final String LIST = "/cards.json";
                public static final String CREATE = "/cards/create.json";
            }
            public static final class Ads{
                public static final String LIST = "/ads.json";
            }
            public static final class Beacons{
                public static final String RANGE = "/beacons/range.json";
            }
            public static final class Transactions{
                public static final String CREATE = "/transactions/create.json";
                public static final String SHOW = "/transactions/show.json";
            }
        }
    }
    public static final String LOG_TAG = "rewards";

}