package com.example.jobattendance.data.repository.preference;

public interface SharedPreferenceSource {
    void putData(String key, boolean val);
    boolean getData(String key);
}
