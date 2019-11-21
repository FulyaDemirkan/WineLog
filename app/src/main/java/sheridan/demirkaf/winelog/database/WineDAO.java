package sheridan.demirkaf.winelog.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import sheridan.demirkaf.winelog.beans.Wine;

@Dao
public interface WineDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWine(Wine wine);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Wine> wines);

    @Delete
    void deleteWine(Wine wine);

    @Query("SELECT * FROM wine WHERE id = :id")
    Wine getWineById(int id);

    @Query("SELECT * FROM wine ORDER BY dateOfVisit DESC")
    LiveData<List<Wine>> getAllWines();

    @Query("DELETE FROM wine")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM wine")
    int getCount();
}
