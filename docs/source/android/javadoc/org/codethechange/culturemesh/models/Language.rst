.. java:import:: android.arch.persistence.room Entity

.. java:import:: android.arch.persistence.room PrimaryKey

.. java:import:: android.net Uri

.. java:import:: com.culturemesh Listable

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: java.io Serializable

Language
========

.. java:package:: com.culturemesh.models
   :noindex:

.. java:type:: @Entity public class Language implements Serializable, Listable

   Represents a language that may be spoken by users. It may be included as part of the definition of a \ :java:ref:`Network`\  or as an attribute of a \ :java:ref:`User`\ , for example.

Fields
------
language_id
^^^^^^^^^^^

.. java:field:: @PrimaryKey public long language_id
   :outertype: Language

   Unique identifier for the language and the \ ``PrimaryKey``\  for databases

name
^^^^

.. java:field:: public String name
   :outertype: Language

   Name of the language, as used by the API.

numSpeakers
^^^^^^^^^^^

.. java:field:: public int numSpeakers
   :outertype: Language

   The number of Culturemesh users who speak the language

Constructors
------------
Language
^^^^^^^^

.. java:constructor:: public Language(long id, String name, int numSpeakers)
   :outertype: Language

   Create a new \ :java:ref:`Language`\  object with the provided properties

   :param id: Unique identifier for the language. The same ID must be used everywhere
   :param name: Human-readable name of the language. This will be displayed to users. It must also be unique, as it is passed in API calls.
   :param numSpeakers: The number of Culturemesh users who speak the language

Language
^^^^^^^^

.. java:constructor:: public Language(JSONObject json) throws JSONException
   :outertype: Language

   Create a new \ :java:ref:`Language`\  from the JSON produced by an API call. The JSON must conform to the following format:

   .. parsed-literal::

      {
                "lang_id": 0,
                "name": "string",
                "num_speakers": 0,
                "added": 0
              }

   Note that the \ ``added``\  key is not used and therefore optional.

   :param json: JSON representation of the language to create.
   :throws JSONException: May be thrown for an improperly formatted JSON

Language
^^^^^^^^

.. java:constructor:: public Language()
   :outertype: Language

   Empty constructor solely for storing Language objects in a database. \ **Never use this!**\

Methods
-------
getListableName
^^^^^^^^^^^^^^^

.. java:method:: public String getListableName()
   :outertype: Language

   Get a descriptive representation of the language suitable for display to user

   :return: Name of the language, abbreviated to be at most \ :java:ref:`Listable.MAX_CHARS`\  characters long.

getNumUsers
^^^^^^^^^^^

.. java:method:: public long getNumUsers()
   :outertype: Language

   Get the number of users who speak the language

   :return: Number of users who speak the language

toString
^^^^^^^^

.. java:method:: public String toString()
   :outertype: Language

   Convert the language to a unique string, its name

   :return: The name of the language

urlParam
^^^^^^^^

.. java:method:: public String urlParam()
   :outertype: Language

   Get a representation of the language suitable for passage in a URL for API calls

   :return: Name of the language encoded for inclusion in a URL

