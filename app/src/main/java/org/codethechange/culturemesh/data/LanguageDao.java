package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.codethechange.culturemesh.models.Language;

/**
 * Created by Drew Gregory on 2/22/18.
 */

@Dao
public interface LanguageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertLanguages(Language... Language);

    @Query("SELECT * FROM language WHERE id = :id")
    public Language getLanguage(long id);
}
