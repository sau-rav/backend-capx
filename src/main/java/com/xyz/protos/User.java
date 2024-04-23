package com.xyz.protos;

public class User {
    private String uid;
    private String email;
    private String name;
    private String referralCode;
    private String referredBy;
    private int refCount;

    // Private constructor to prevent instantiation from outside the class
    private User(Builder builder) {
        this.uid = builder.uid;
        this.email = builder.email;
        this.name = builder.name;
        this.referralCode = builder.referralCode;
        this.referredBy = builder.referredBy;
        this.refCount = builder.refCount;
    }

    // Getters
    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public int getRefCount() {
        return refCount;
    }

    // Builder class for User
    public static class Builder {
        private String uid;
        private String email;
        private String name;
        private String referralCode;
        private String referredBy;
        private int refCount;

        public Builder(String uid, String email) {
            this.uid = uid;
            this.email = email;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setReferralCode(String referralCode) {
            this.referralCode = referralCode;
            return this;
        }

        public Builder setReferredBy(String referredBy) {
            this.referredBy = referredBy;
            return this;
        }

        public Builder setRefCount(int refCount) {
            this.refCount = refCount;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

