package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.codethechange.culturemesh.models.PostReply;

import java.util.List;

/**
 * Created by Drew Gregory on 3/4/18.
 */
@Dao
public interface PostReplyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertPostReplies(PostReply... posts);

    @Query("SELECT * FROM postreply WHERE id LIKE :pId")
    public PostReply getPostReply(long pId);

    @Query("SELECT * FROM postreply WHERE parentId LIKE :pId")
    public List<PostReply> getPostReplies(long pId);
}
