package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.codethechange.culturemesh.models.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Drew Gregory on 2/19/18.
 */
@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertPosts(Post... posts);

    @Query("SELECT * FROM post WHERE id LIKE :pId")
    public Post getPost(int pId);

    @Query("SELECT * FROM post WHERE networkId=:id")
    public List<Post> getNetworkPosts(int id);

    @Query("SELECT * FROM post WHERE userId=:id")
    public List<Post> getUserPosts(long id);

}
