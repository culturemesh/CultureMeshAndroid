.. java:import:: com.culturemesh.android CreateEventActivity

.. java:import:: com.culturemesh.android FormatManager

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: java.io Serializable

Event
=====

.. java:package:: com.culturemesh.android.models
   :noindex:

.. java:type:: public class Event extends FeedItem implements Serializable, Putable, Postable

   Describes an event like those shared in \ :java:ref:`Network`\ s

Fields
------
NOWHERE
^^^^^^^

.. java:field:: public static final String NOWHERE
   :outertype: Event

   Value other classes should pass to this class and should expect to receive from this class to represent the portions of addresses that are not a part of the address. Note that \ :java:ref:`Event.getAddress()`\  uses this constant only when the entire address is missing.

addressLine1
^^^^^^^^^^^^

.. java:field:: public String addressLine1
   :outertype: Event

   First line of the address where the event is to take place. Some addresses may not have this value, in which case its value will be \ :java:ref:`Event.NOWHERE_INTERNAL`\ .

addressLine2
^^^^^^^^^^^^

.. java:field:: public String addressLine2
   :outertype: Event

   Second line of the address where the event is to take place. Some addresses may not have this value, in which case its value will be \ :java:ref:`Event.NOWHERE_INTERNAL`\ .

authorId
^^^^^^^^

.. java:field:: public long authorId
   :outertype: Event

   Unique identifier of the \ :java:ref:`User`\  who created the event

city
^^^^

.. java:field:: public String city
   :outertype: Event

   City portion of the address where the event is to take place. Some addresses may not have this value, in which case its value will be \ :java:ref:`Event.NOWHERE_INTERNAL`\ .

country
^^^^^^^

.. java:field:: public String country
   :outertype: Event

   Country portion of the address where the event is to take place. Some addresses may not have this value, in which case its value will be \ :java:ref:`Event.NOWHERE_INTERNAL`\ .

description
^^^^^^^^^^^

.. java:field:: public String description
   :outertype: Event

   User-generated description of the event. May contain formatting from \ :java:ref:`FormatManager`\ .

   **See also:** :java:ref:`CreateEventActivity`

id
^^

.. java:field:: public long id
   :outertype: Event

   A unique identifier for the event. This should be generated server-side.

networkId
^^^^^^^^^

.. java:field:: public long networkId
   :outertype: Event

   Unique identifier corresponding to the \ :java:ref:`Network`\  the \ :java:ref:`Event`\  is shared within

region
^^^^^^

.. java:field:: public String region
   :outertype: Event

   Region portion of the address where the event is to take place. Some addresses may not have this value, in which case its value will be \ :java:ref:`Event.NOWHERE_INTERNAL`\ .

timeOfEvent
^^^^^^^^^^^

.. java:field:: public String timeOfEvent
   :outertype: Event

   Date and time of the event which must strictly conform to \ ``yyyy-MM-ddTHH:mm:ss.SSSZ``\ . For example, \ ``2015-01-01T15:00:00.000Z``\  is an acceptable value.

title
^^^^^

.. java:field:: public String title
   :outertype: Event

   User-generated title for the event. Generally short (one line).

Constructors
------------
Event
^^^^^

.. java:constructor:: public Event(long id, long networkId, String title, String description, String timeOfEvent, long author, String addressLine1, String addressLine2, String city, String region, String country)
   :outertype: Event

   Construct an Event object from the provided parameters.

   :param id: Unique identifier for the event
   :param networkId: Unique identifier for the \ :java:ref:`Network`\  the event is a part of
   :param title: User-generated title for the event
   :param description: User-generated description of the event
   :param timeOfEvent: Date and time of the event. Must strictly conform to the format \ ``yyyy-MM-ddTHH:mm:ss.SSSZ``\ .
   :param author: Unique identifier for the \ :java:ref:`User`\  creating the \ :java:ref:`Event`\
   :param addressLine1: Optional first line of the address. \ :java:ref:`Event.NOWHERE`\  if absent.
   :param addressLine2: Optional second line of the address. \ :java:ref:`Event.NOWHERE`\  if absent.
   :param city: Optional city portion of the address. \ :java:ref:`Event.NOWHERE`\  if absent.
   :param region: Optional region portion of the address. \ :java:ref:`Event.NOWHERE`\  if absent.
   :param country: Optional country portion of the address. \ :java:ref:`Event.NOWHERE`\  if absent.

Event
^^^^^

.. java:constructor:: public Event()
   :outertype: Event

   Empty constructor that does nothing to initialize any instance fields. For database use only.

Event
^^^^^

.. java:constructor:: public Event(JSONObject json) throws JSONException
   :outertype: Event

   Create a new Event object from a JSON representation that conforms to the following format:

   .. parsed-literal::

      {
               "id": 0,
               "id_network": 0,
               "id_host": 0,
               "date_created": "string",
               "event_date": "2018-06-23T04:39:42.600Z",
               "title": "string",
               "address_1": "string",
               "address_2": "string",
               "country": "string",
               "city": "string",
               "region": "string",
               "description": "string"
              }

   Note that \ ``date_created``\  is not used and may be omitted. Empty address fields should be \ ``null``\ .

   :param json: JSON representation of the \ :java:ref:`Event`\  to be created
   :throws JSONException: May be thrown if an improperly formatted JSON is provided

Methods
-------
getAddress
^^^^^^^^^^

.. java:method:: public String getAddress()
   :outertype: Event

   Generate a formatted form of the address for the event that is suitable for display to user.

   :return: UI-suitable form of the address where the event will take place. Address portions (line1, line2, city, region, and country) are separated by commas, and missing portions are excluded. Example: \ ``123 Any Street, New York, New York``\ . The address portions are user-generated, so this String may not describe a valid address. If no address is specified (i.e. if all address portions are missing), the \ :java:ref:`Event.NOWHERE`\  constant is returned.

getAuthor
^^^^^^^^^

.. java:method:: public long getAuthor()
   :outertype: Event

   Get the unique identifier of the \ :java:ref:`User`\  who created the event

   :return: Unique identifier of event author

getDescription
^^^^^^^^^^^^^^

.. java:method:: public String getDescription()
   :outertype: Event

   Get the author-generated description of the \ :java:ref:`Event`\

   :return: Text the \ :java:ref:`User`\  wrote to describe the event

getPostJson
^^^^^^^^^^^

.. java:method:: public JSONObject getPostJson() throws JSONException
   :outertype: Event

   Create a JSON representation of the object that conforms to the following format:

   .. parsed-literal::

      {
                 "id_network": 0,
                 "id_host": 0,
                 "event_date": "2018-07-21T15:10:30.838Z",
                 "title": "string",
                 "address_1": "string",
                 "address_2": "string",
                 "country": "string",
                 "city": "string",
                 "region": "string",
                 "description": "string"
              }

   This is intended to be the format used by the \ ``/event/new``\  POST endpoint.

   :throws JSONException: Unclear when this would be thrown
   :return: JSON representation of the object

getPutJson
^^^^^^^^^^

.. java:method:: public JSONObject getPutJson() throws JSONException
   :outertype: Event

   Create a JSON representation of the object that conforms to the following format:

   .. parsed-literal::

      {
                  "id": 0,
                  "id_network": 0,
                  "id_host": 0,
                  "event_date": "2018-07-21T15:10:30.838Z",
                  "title": "string",
                  "address_1": "string",
                  "address_2": "string",
                  "country": "string",
                  "city": "string",
                  "region": "string",
                  "description": "string"
               }

   This is intended to be the format used by the \ ``/event/new``\  PUT endpoint.

   :throws JSONException: Unclear when this would be thrown
   :return: JSON representation of the object

getTimeOfEvent
^^^^^^^^^^^^^^

.. java:method:: public String getTimeOfEvent()
   :outertype: Event

   Get the date and time of the event

   :return: Timestamp for the event, which will be formatted as \ ``yyyy-MM-ddTHH:mm:ss.SSSZ``\

getTitle
^^^^^^^^

.. java:method:: public String getTitle()
   :outertype: Event

   Get the author-generated title for the \ :java:ref:`Event`\

   :return: Title the \ :java:ref:`User`\  chose to describe the event

setAuthor
^^^^^^^^^

.. java:method:: public void setAuthor(User author)
   :outertype: Event

   Set the ID of the event's author. WARNING: The same ID must be used for a given \ :java:ref:`User`\  across CultureMesh.

   :param author: Unique identifier of the \ :java:ref:`User`\  who created the event.

setDescription
^^^^^^^^^^^^^^

.. java:method:: public void setDescription(String description)
   :outertype: Event

   Set the author-generated description of the \ :java:ref:`Event`\

   :param description: Text the \ :java:ref:`User`\  wrote to describe the event

setTimeOfEvent
^^^^^^^^^^^^^^

.. java:method:: public void setTimeOfEvent(String timeOfEvent)
   :outertype: Event

   Set the date and time of the event

   :param timeOfEvent: Timestamp for when the event will occur. Must strictly conform to \ ``yyyy-MM-ddTHH:mm:ss.SSSZ``\ .

setTitle
^^^^^^^^

.. java:method:: public void setTitle(String title)
   :outertype: Event

   Set the author-generated title for the \ :java:ref:`Event`\

   :param title: Title the \ :java:ref:`User`\  chose to describe the event

