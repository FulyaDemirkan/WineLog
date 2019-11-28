package sheridan.demirkaf.winelog.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.room.TypeConverter;


public class StringListConverter {

    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        if (value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        if (list == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.toJson(list, listType);
    }
}
