.. java:import:: android.arch.persistence.room Entity

.. java:import:: android.arch.persistence.room Ignore

.. java:import:: android.arch.persistence.room PrimaryKey

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: java.io Serializable

.. java:import:: java.math BigInteger

.. java:import:: java.text DateFormat

.. java:import:: java.text ParseException

.. java:import:: java.text SimpleDateFormat

.. java:import:: java.util Date

.. java:import:: java.util Locale

Post
====

.. java:package:: com.culturemesh.models
   :noindex:

.. java:type:: @Entity public class Post extends FeedItem implements Serializable, Postable, Putable

   Represents a post made by a user in a network. A post is arbitrary, formatted text of the user's choosing.

Fields
------
author
^^^^^^

.. java:field:: @Ignore public User author
   :outertype: Post

   The \ :java:ref:`User`\  who created the post. This may not be present and have to be instantiated from \ :java:ref:`Post.userId`\ . Currently, this is handled by \ :java:ref:`com.culturemesh.API`\

content
^^^^^^^

.. java:field:: public String content
   :outertype: Post

   The body of the post. May be formatted.

   **See also:** :java:ref:`com.culturemesh.FormatManager`

datePosted
^^^^^^^^^^

.. java:field:: public String datePosted
   :outertype: Post

   Timestamp for when the post was created. Should conform to \ ``EEE, dd MMM yyyy kk:mm:ss z``\

id
^^

.. java:field:: @PrimaryKey public long id
   :outertype: Post

   Uniquely identifies the post across all of CultureMesh

imgLink
^^^^^^^

.. java:field:: public String imgLink
   :outertype: Post

   Link to an image, if available, that is associated with the post

network
^^^^^^^

.. java:field:: @Ignore public Network network
   :outertype: Post

   The \ :java:ref:`Network`\  who created the post. This may not be present and have to be instantiated from \ :java:ref:`Post.networkId`\ . Currently, this is handled by \ :java:ref:`com.culturemesh.API`\

networkId
^^^^^^^^^

.. java:field:: public long networkId
   :outertype: Post

   Unique identifier for the network the post was made in. This is used when only a reference to the full \ :java:ref:`Network`\  object is needed, e.g. when getting a post from the API. The rest of the information associated with the network can be fetched later.

userId
^^^^^^

.. java:field:: public long userId
   :outertype: Post

   Unique identifier for the user who created the post. This is used when only a reference to the full \ :java:ref:`User`\  object is needed, e.g. when getting a post from the API. The rest of the information associated with the user can be fetched later.

vidLink
^^^^^^^

.. java:field:: public String vidLink
   :outertype: Post

   Link to a video, if available, that is associated with the post TODO: Handle multiple links?

Constructors
------------
Post
^^^^

.. java:constructor:: public Post(long id, long author, long networkId, String content, String imgLink, String vidLink, String datePosted)
   :outertype: Post

   Create a new post object from the provided parameters. The resulting object will not be fully instantiated (e.g. \ :java:ref:`Post.author`\  and \ :java:ref:`Post.network`\  will be \ ``null``\ .

   :param id: Uniquely identifies the post across all of CultureMesh
   :param author: ID of \ :java:ref:`User`\  who created the post
   :param networkId: ID of the \ :java:ref:`Network`\  in which the post was made
   :param content: Formatted text that composes the body of the post.
   :param imgLink: Link to an image associated with the post. \ ``null``\  if none associated.
   :param vidLink: Link to a video associated with the post. \ ``null``\  if none associated
   :param datePosted: When the post was created. Must conform to \ ``EEE, dd MMM yyyy kk:mm:ss z``\

   **See also:** :java:ref:`com.culturemesh.FormatManager`

Post
^^^^

.. java:constructor:: public Post()
   :outertype: Post

   Empty constructor for database

Post
^^^^

.. java:constructor:: public Post(JSONObject json) throws JSONException
   :outertype: Post

   Creates a bare (uninstantiated) \ :java:ref:`Post`\  from a JSON that conforms to the below format:

   .. parsed-literal::

      {
              "id": 0,
              "id_user": 0,
              "id_network": 0,
              "post_date": "string",
              "post_text": "string",
              "post_class": 0,
              "post_original": "string",
              "vid_link": "string",
              "img_link": "string"
             }

   :param json: JSON representation of the \ :java:ref:`Post`\  to construct
   :throws JSONException: May be thrown in response to an improperly formatted JSON

Methods
-------
getAuthor
^^^^^^^^^

.. java:method:: public User getAuthor()
   :outertype: Post

   Get the author of the post. Object must be fully instantiated, not just populated
   with IDs

   :return: Author of the post

getContent
^^^^^^^^^^

.. java:method:: public String getContent()
   :outertype: Post

   Get the formatted text that makes up the body of the post.

   :return: Body of the post, which may be formatted.

   **See also:** :java:ref:`com.culturemesh.FormatManager`

getDatePosted
^^^^^^^^^^^^^

.. java:method:: public String getDatePosted()
   :outertype: Post

   Get when the post was created.

   :return: Timestamp of when post was created. Conforms to \ ``EEE, dd MMM yyyy kk:mm:ss z``\

getImageLink
^^^^^^^^^^^^

.. java:method:: public String getImageLink()
   :outertype: Post

   Get the URL to the image associated with the post.

   :return: URL to associated image. If no image is associated, \ ``null``\

getNetwork
^^^^^^^^^^

.. java:method:: public Network getNetwork()
   :outertype: Post

   Get the network of the post. Object must be fully instantiated, not just populated
   with IDs

   :return: Network of the post

getPostJson
^^^^^^^^^^^

.. java:method:: public JSONObject getPostJson() throws JSONException
   :outertype: Post

   Wrapper for \ :java:ref:`Post.toJSON()`\

getPostedTime
^^^^^^^^^^^^^

.. java:method:: public Date getPostedTime() throws ParseException
   :outertype: Post

   Sometimes, we will want to get the time not just as a string but as a Date object (i.e. for comparing time for sorting)

   :return: Date object based on datePosted string.

getPutJson
^^^^^^^^^^

.. java:method:: public JSONObject getPutJson() throws JSONException
   :outertype: Post

   Wrapper for \ :java:ref:`Post.toJSON()`\

getVideoLink
^^^^^^^^^^^^

.. java:method:: public String getVideoLink()
   :outertype: Post

   Get the URL to the video associated with the post.

   :return: URL to associated video. If no video is associated, \ ``null``\

setContent
^^^^^^^^^^

.. java:method:: public void setContent(String content)
   :outertype: Post

   Set the body of the post to the parameter provided.

   :param content: Formatted body of the post.

   **See also:** :java:ref:`com.culturemesh.FormatManager`

setDatePosted
^^^^^^^^^^^^^

.. java:method:: public void setDatePosted(String datePosted)
   :outertype: Post

   Get the timestamp for when the post was created.

   :param datePosted: When post was created. Conforms to \ ``EEE, dd MMM yyyy kk:mm:ss z``\

setImageLink
^^^^^^^^^^^^

.. java:method:: public void setImageLink(String imgLink)
   :outertype: Post

   Associate the image at the provided URL with the post. Replaces any existing image URL.

   :param imgLink: URL to the image to add to the post

setVideoLink
^^^^^^^^^^^^

.. java:method:: public void setVideoLink(String vidLink)
   :outertype: Post

   Associate the video at the provided URL with the post. Replaces any existing video URL.

   :param vidLink: URL to the video to add to the post

toJSON
^^^^^^

.. java:method:: public JSONObject toJSON() throws JSONException
   :outertype: Post

   Generate a JSON describing the object. The JSON will conform to the following format:

   .. parsed-literal::

      {
                     "id_user": 0,
                     "id_network": 0,
                     "post_text": "string",
                     "vid_link": "string",
                     "img_link": "string"
                 }

   The resulting object is suitable for use with the \ ``/post/new``\  endpoint (PUT and POST).

   :throws JSONException: Unclear when this would be thrown
   :return: JSON representation of the object

