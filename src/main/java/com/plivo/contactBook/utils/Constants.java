package com.plivo.contactBook.utils;

public class Constants {
    public static class Constraints {
        public static final int CONTACT_NUM = 10;
        public static final int ADDRESS_MAX = 200;
        public static final int NAME_MIN = 2;
        public static final int NAME_MAX = 100;
        public static final int PASSWORD_MIN = 12;
        public static final int PASSWORD_MAX = 100;
    }

    public class Sort {
        public static final String ASCENDING = "asc";
        public static final String DESCENDING = "desc";
    }

    public class StatusCodes {
        public static final int SUCCESS = 200;
        public static final int CREATED = 201;
        public static final int BAD_REQUEST = 400;
        public static final int UNAUTHORIZED = 401;
        public static final int NOT_FOUND = 404;
        public static final int INTERNAL_SERVER_ERROR = 500;
    }

    public class ErrorMessage {
        public static final String INVALID_CREDENTIALS = "Invalid credentials";
        public static final String INVALID_PARAMS = "Invalid parameters";
    }

    public class Roles {
        public static final String USER = "user";
    }

    public class Token {
        public static final String HEADER_STRING = "Authorization";
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final long EXPIRY = 7200000L;
    }

    public class RequestAttributes {
        public static final String TOKEN = "TOKEN";
        public static final String CONTEXT = "CONTEXT";
    }
}
