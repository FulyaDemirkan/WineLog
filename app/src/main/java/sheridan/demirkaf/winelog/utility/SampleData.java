package sheridan.demirkaf.winelog.utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import sheridan.demirkaf.winelog.beans.Wine;

public class SampleData {

    private static final String SAMPLE_WINE_TITLE_1 = "Wine1";
    private static final String SAMPLE_WINE_TITLE_2 = "Wine2";
    private static final String SAMPLE_WINE_TITLE_3 = "Wine3";

    private static final String SAMPLE_WINE_YEAR_1 = "2011";
    private static final String SAMPLE_WINE_YEAR_2 = "2012";
    private static final String SAMPLE_WINE_YEAR_3 = "2013";

    private static final String SAMPLE_WINE_WINERY_1 = "Winery1";
    private static final String SAMPLE_WINE_WINERY_2 = "Winery2";
    private static final String SAMPLE_WINE_WINERY_3 = "Winery3";

    private static final String SAMPLE_WINE_CATEGORY_1 = "Red Wine";
    private static final String SAMPLE_WINE_CATEGORY_2 = "White Wine";
    private static final String SAMPLE_WINE_CATEGORY_3 = "Ice Wine";

    private static final String SAMPLE_WINE_TYPE_1 = "Cabernet Sauvignon";
    private static final String SAMPLE_WINE_TYPE_2 = "Riesling";
    private static final String SAMPLE_WINE_TYPE_3 = "Sauvignon Blanc";

    private static final String SAMPLE_WINE_STYLE_1 = "Full-Bodied & Firm";
    private static final String SAMPLE_WINE_STYLE_2 = "Light-Bodied & Fruity";
    private static final String SAMPLE_WINE_STYLE_3 = "Medium-Bodied & Fruity";

    private static final int SAMPLE_WINE_OAK_1 = 25;
    private static final int SAMPLE_WINE_OAK_2 = 50;
    private static final int SAMPLE_WINE_OAK_3 = 75;

    private static final int SAMPLE_WINE_FLAVOUR_1 = 75;
    private static final int SAMPLE_WINE_FLAVOUR_2 = 50;
    private static final int SAMPLE_WINE_FLAVOUR_3 = 25;

    private static final ArrayList<String> SAMPLE_WINE_MAIN_FLAV_1 = new ArrayList<String>() {
        {
            add("flav1");
            add("flav2");
            add("flav3");
        }
    };
    private static final ArrayList<String> SAMPLE_WINE_MAIN_FLAV_2 = new ArrayList<String>() {
        {
            add("flav4");
            add("flav5");
            add("flav6");
        }
    };
    private static final ArrayList<String> SAMPLE_WINE_MAIN_FLAV_3 = new ArrayList<String>() {
        {
            add("flav7");
            add("flav8");
            add("flav9");
        }
    };

    private static final float SAMPLE_WINE_RATING_1 = 5;
    private static final float SAMPLE_WINE_RATING_2 = 4;
    private static final float SAMPLE_WINE_RATING_3 = 3.5F;

    private static final String SAMPLE_WINE_NOTES_1 = "Wine1 random notes";
    private static final String SAMPLE_WINE_NOTES_2 = "Wine2 random notes";
    private static final String SAMPLE_WINE_NOTES_3 = "Wine3 random notes";

    // src: http://freeiconshop.com/icon/wine-icon-flat/
    private static final String SAMPLE_WINE_IMG_1 = "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";
    private static final String SAMPLE_WINE_IMG_2 = "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";
    private static final String SAMPLE_WINE_IMG_3 = "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";


    private static final String SAMPLE_WINE_DESC_1 = "Create a todo list with options to add, edit" +
            "and delete notes";
    private static final String SAMPLE_WINE_DESC_2 = "Eggs\nMilk\nChocolate\nBananas";
    private static final String SAMPLE_WINE_DESC_3 = "Need to study Spring Boot and API Endpoints";


    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, diff);
        return cal.getTime();
    }

    public static List<Wine> getWines() {
        List<Wine> wines = new ArrayList<>();
        // diff is used for sorting by date
        wines.add(new Wine(SAMPLE_WINE_TITLE_1, SAMPLE_WINE_YEAR_1, SAMPLE_WINE_CATEGORY_1, SAMPLE_WINE_TYPE_1, SAMPLE_WINE_WINERY_1,
                getDate(0), SAMPLE_WINE_STYLE_1, SAMPLE_WINE_OAK_1, SAMPLE_WINE_FLAVOUR_1,
                SAMPLE_WINE_MAIN_FLAV_1, SAMPLE_WINE_RATING_1, SAMPLE_WINE_NOTES_1,
                SAMPLE_WINE_IMG_1));
        wines.add(new Wine(SAMPLE_WINE_TITLE_2, SAMPLE_WINE_YEAR_2, SAMPLE_WINE_CATEGORY_2, SAMPLE_WINE_TYPE_2, SAMPLE_WINE_WINERY_2,
                getDate(3), SAMPLE_WINE_STYLE_2, SAMPLE_WINE_OAK_2, SAMPLE_WINE_FLAVOUR_2,
                SAMPLE_WINE_MAIN_FLAV_2, SAMPLE_WINE_RATING_2, SAMPLE_WINE_NOTES_2,
                SAMPLE_WINE_IMG_2));
        wines.add(new Wine(SAMPLE_WINE_TITLE_3, SAMPLE_WINE_YEAR_3, SAMPLE_WINE_CATEGORY_3, SAMPLE_WINE_TYPE_3, SAMPLE_WINE_WINERY_3,
                getDate(5), SAMPLE_WINE_STYLE_3, SAMPLE_WINE_OAK_3, SAMPLE_WINE_FLAVOUR_3,
                SAMPLE_WINE_MAIN_FLAV_3, SAMPLE_WINE_RATING_3, SAMPLE_WINE_NOTES_3,
                SAMPLE_WINE_IMG_3));
        return wines;
    }
}
