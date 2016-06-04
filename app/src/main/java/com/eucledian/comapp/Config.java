package com.eucledian.comapp;


import com.eucledian.comapp.dao.AppUserDataSource;
import com.eucledian.comapp.dao.AppUserMarkerDataSource;
import com.eucledian.comapp.dao.AppUserSurveyDataSource;
import com.eucledian.comapp.dao.AppUserSurveyResponseDataSource;
import com.eucledian.comapp.dao.MarkerDataSource;
import com.eucledian.comapp.dao.SurveyDataSource;
import com.eucledian.comapp.dao.SurveyFieldDataSource;
import com.eucledian.comapp.dao.SurveyFieldOptionDataSource;
import com.eucledian.comapp.dao.SurveyFieldValidationDataSource;
import com.eucledian.comapp.dao.TokenDataSource;
import com.eucledian.comapp.dao.ZoneDataSource;

public class Config {

    public static final class Database {
        public static final String NAME = "rewards.db";
        public static final int VERSION = 1;
        public static final String[] SQL_CREATE_STMT = {
                TokenDataSource.CREATE_TABLE,
                AppUserDataSource.CREATE_TABLE,
                MarkerDataSource.CREATE_TABLE,
                ZoneDataSource.CREATE_TABLE,
                SurveyDataSource.CREATE_TABLE,
                SurveyFieldDataSource.CREATE_TABLE,
                SurveyFieldOptionDataSource.CREATE_TABLE,
                SurveyFieldValidationDataSource.CREATE_TABLE,
                AppUserMarkerDataSource.CREATE_TABLE,
                AppUserSurveyDataSource.CREATE_TABLE,
                AppUserSurveyResponseDataSource.CREATE_TABLE
        };
        public static final String[] SQL_DELETE_STMT = {
                TokenDataSource.DROP_TABLE,
                AppUserDataSource.DROP_TABLE,
                MarkerDataSource.DROP_TABLE,
                ZoneDataSource.DROP_TABLE,
                SurveyDataSource.DROP_TABLE,
                SurveyFieldDataSource.DROP_TABLE,
                SurveyFieldOptionDataSource.DROP_TABLE,
                SurveyFieldValidationDataSource.DROP_TABLE,
                AppUserMarkerDataSource.DROP_TABLE,
                AppUserSurveyDataSource.DROP_TABLE,
                AppUserSurveyResponseDataSource.DROP_TABLE
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
        /**public static final String ENDPOINT = "http://192.168.15.5";
        public static final String PORT = "3000";
        public static final String SCOPE = "";*/
        // Staging
        public static final String ENDPOINT = "http://hq.eucledian.com";
        public static final String PORT = "4000";
        public static final String SCOPE = "";

        // Global
        public static final String CONTROLLER = "/api";
        public static final String IMAGE = "";
        public static final class Urls{
            public static final class Users{
                public static final String LOGIN = "/users/login";
            }
            public static final class Surveys{
                public static final String LIST = "/surveys";
                public static final String SYNC = "/surveys/:survey_id";
            }
            public static final class Markers{
                public static final String LIST = "/markers";
                public static final String SYNC = "/markers";
            }
        }
    }
    public static final String LOG_TAG = "comapp";

}