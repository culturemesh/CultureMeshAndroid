=================
Structure of Code
=================

--------------
User Interface
--------------
The screens displayed to users are called activities. Each one has an associated
``*Activity.java`` file that defines a ``*Activity`` class. For example, the
timeline for a network has an associated :java:ref:`TimelineActivity` that
controls it. Each activity may also include fragments
(e.g. :java:ref:`ListNetworksFragment`) that define part of an activity
and can be reused across multiple activities. They are also often used for parts
of the activity that sometimes disappear or are exchanged with other fragments.
Each activity and fragment may also have layouts defined in the ``res`` folder
as ``activity_*.xml`` and ``content_*.xml``.

Adapters
========
In some activities, large scrollable lists need to be displayed. Generating the
displayable list elements (``View`` s) for all the items is inefficient, so
``RecyclerView`` s are used, which efficiently handle generating the list using
adapters. These classes (e.g. :java:ref:`RVAdapter`) provide the functionality
``RecyclerView`` needs to dynamically generate each displayed list element.

-----------
Data Models
-----------
Conceptually, the data stored with CultureMesh, including :java:ref:`Place` s,
:java:ref:`User` s, :java:ref:`Network` s, and :java:ref:`Event` s are represented
as models. These models are represented in JSON when in transit between the app
and the server and as instances of the appropriate class
(see :java:ref:`models`) within the app. The :java:ref:`API` class handles
converting between object and JSON formats, often using constructors and getters
within the model's class (e.g. :java:ref:`Post.getPostJSON`).

Places and Locations
====================
:java:ref:`Place` s and/or :java:ref:`Location` s are part of the definition of a
:java:ref:`Network`, and they are used by themselves when displaying lists from
which the user can choose parameters to narrow their search for networks.

The difference between a place and a location is not well captured in their
names. A place defines a particular geographic area that is one of three types:
a :java:ref:`City`, a :java:ref:`Region`, or a :java:ref:`Country`. A location
can be any of those three, and includes definitions for one or more places.
For example, a location might refer to San Francisco, in which case it would
store ``San Francisco, California, United States``.

Places can also store references to parent places, so this distinction may seem
unhelpful. However, we use it because the salient difference between a location
and a place is that a place is a separate model that stores all the information
CultureMesh has on that place (e.g. name, population, etc.). On the other hand,
a location only stores the IDs of the places that define it. In practice, this
means that places can be thought of as residing in a list of all places
CultureMesh knows about, while locations are used to define networks.

Inheritance Structure
---------------------

.. code-block:: plain

                      Location
                     (IDs only)
                     /        \
                    /          \
                   /            \
                  /              \
         Place (Abstract)    DatabaseLocation (Abstract)
          (Full Info)                   (IDs)
            /  |  \                   /      \
           /   |   \           NearLocation  FromLocation
      City  Region  Country  (Wrappers for DatabaseLocation)
   (Specific cases of Place)

The diagram above illustrates the inheritance hierarchy consisting of classes
storing location/place information. The tree rooted at
:java:ref:`DatabaseLocation` exists because of the potential to cache data
locally in a database. This would allow for offline access and better
performance when internet connection is poor. However, the database
we experimented with required that the near (or current) location be specified
using a different class than the from (or origin) location so that their
instance fields could have different names and not conflict in the database.
This is why :java:ref:`NearLocation` and :java:ref:`FromLocation` exist, as they
are otherwise essentially the same. Whenever they can be treated identically,
:java:ref:`DatabaseLocation` can be used. DatabaseLocation also stores
functionality that is common to both subclasses.

Networks, Languages, Events, and Posts
======================================
A :java:ref:`Network` is defined in one of two ways:

* Location-based: The network is defined by a :java:ref:`NearLocation` and a
  :java:ref:`FromLocation`.
* Language-based: The network is defined by a :java:ref:`NearLocation` and a
  :java:ref:`Language`.

When the network is initially received from the server as a JSON, it is parsed
to create a :java:ref:`DatabaseNetwork`, which represents the above properties
by their IDs. Then, that DatabaseNetwork is expanded into a :java:ref:`Network`,
which includes full :java:ref:`Place` and/or :java:ref:`Language` objects for
the above properties.

While not stored in the Network object, there are also lists of
:java:ref:`Event` s and :java:ref:`Post` s associated with each network. These
are fetched separately from the server each time they are needed. Instead of
separate classes for their ID-only representations coming from the server and
the fuller ones used within the app, they are instantiated in stages within the
:java:ref:`API` class. First, their JSON representations are parsed to partially
instantiate them. Then, missing parts (e.g. full Network objects) are fetched
from the server and parsed to fully instantiate the objects.

Both Event and Post are subclasses of :java:ref:`FeedItem`, which requires them
to have a public instance field containing a list of comments. This allows them
to both be displayed via polymorphism within a feed like
:java:ref:`TimelineActivity`. These comments are represented by
:java:ref:`PostReply` objects.

Interfaces for Sending Objects
==============================
To reduce code redundancy, the :java:ref:`API` class uses a series of ``model``
methods that can send ``PUT`` and ``POST`` requests (separate ``model`` methods)
with any object so long as that object can generate a JSON representation of
itself for the request using ``getPutJSON`` or ``getPostJSON``. The presence
of these methods is enforced by the interfaces :java:ref:`Postable` and
:java:ref:`Putable`, which allows for the ``model`` methods to be polymorphic.

Other
=====
A :java:ref:`Point` describes a particular spot on the globe in terms of its
latitude and longitude. It is really just a holder for the two values.

A :java:ref:`User` object represents any of CultureMesh's users. It only stores
parts of their public profiles, so methods that work with private information
like passwords or email addresses take those values as parameters.

------------------------------------
Connections to CultureMesh's Servers
------------------------------------
Networking operations are performed by making calls to methods in the
:java:ref:`API` class. Since networking operations suffer from any inherent
latency in the user's internet connection, they are performed in a separate
thread using `Volley <https://developer.android.com/training/volley/>`_.
Generically then, these methods generally take the following arguments:
``(RequestQueue, args ... , Response.Listener<responseType>)``

* ``RequestQueue``: A queue that holds the asynchronous tasks to execute.
  A queue is generally created once for each activity and then used for all
  API calls in that activity.
* ``args``: All the arguments the method needs to create the network request.
  This often includes IDs of resources to fetch.
* ``Response.Listener<...>``: A listener whose ``onResponse`` method is called
  with the result of the operation. This occurs whether or not the operation
  completed successfully.
* ``responseType``: The type of the object that is returned by the operation.
  This is generally some kind of :java:ref:`NetworkResponse` object.

API Authentication
==================

API Key
-------
The API key must be passed as a parameter with key ``key`` in the URL of all
authenticated API endpoints. The key is stored in :java:ref:`Credentials`, which
is not stored in version control or published publicly. The API method
:java:ref:`API.getCredentials` method is used to access the key from within the
:java:ref:`API` class.

User Credentials
----------------
When the user logs in to the app the first time, their email and password
are used to authenticate a request for a login token using
:java:ref:`API.Get.loginWithCred`. This token is stored in the app's
`SharedPreferences <https://developer.android.com/reference/android/content/SharedPreferences>`_
for future authentication. The user's password is not stored. If the token
expires due to inactivity, the user is directed to login again.

All tokens older than :java:ref:`API.TOKEN_REFRESH`
milliseconds are refreshed with the next authenticated request (this is handled
automatically by :java:ref:`API.Get.loginToken`, which produces the tokens used
by all API methods that access secured endpoints). Tokens are refreshed much
faster than they expire because the difference between the refresh time and the
expiration time is the maximum allowable inactivity period before users have to
sign in again, and we want this to be long enough to avoid too much
inconvenience.

Conveying Network Responses
===========================
This object simplifies error reporting by storing whether or not the operation
failed using :java:ref:`NetworkResponse.fail`. It also stores the results
of successful operations, which are available through
:java:ref:`NetworkResponse.getPayload`. It can store messages describing errors
and create ready-to-display error dialogs to communicate those messages to
users using :java:ref:`NetworkResponse.showErrorDialog`.

Authentication Failures
-----------------------
In the special case of
authentication errors, the :java:ref:`NetworkResponse.setAuthFailed` method can
be used to specify that the failure was authentication-related. When the
resulting error dialog is displayed and dismissed, the user is automatically
redirected to the sign-in screen.

Recommended Usage
=================
* Specify the network operation to be performed in a method in the
  :java:ref:`API` class. The method should take a ``RequestQueue`` and a
  ``Response.Listener``.

  * Create the request, such as ``JsonObjectRequest``, providing the method of
    the request (e.g. ``GET``, ``POST``, etc.), endpoint URL, listener, and
    error listener.
  * In the listener, specify an ``onResponse`` method that
    handles interpreting the response into a :java:ref:`NetworkResponse` and
    passing that to a call to the ``Response.Listener`` provided as a parameter
    to the API method.
  * In the error listener, interpret the error and select an appropriate error
    message. Create a :java:ref:`NetworkResponse` object to communicate the
    error. If appropriate, use :java:ref:`NetworkResponse.setAuthFailed`.
  * Example method:

    .. code-block:: java

      static void user(RequestQueue queue, long id,
                        final Response.Listener<NetworkResponse<User>> listener) {
          JsonObjectRequest authReq = new JsonObjectRequest(Request.Method.GET,
                  API_URL_BASE + "user/" + id + "?" + getCredentials(),
                  null, new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject res) {
                  try {
                      //make User object out of user JSON.
                      User user = new User(res);
                      listener.onResponse(new NetworkResponse<>(false, user));
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  listener.onResponse(new NetworkResponse<User>(true,
                          processNetworkError("API.Get.user", "ErrorListener", error)));
              }
          });
          queue.add(authReq);
      }

    Note that :java:ref:`API.API_URL_BASE` is a constant in the API class
    that specifies the base of the API URLs,
    that :java:ref:`API.processNetworkError` returns a reference to a message
    describing the error, and that :java:ref:`API.getCredentials` returns the
    API key.

* In any API methods that rely on another API method, call the used method as
  usual, but do anything that relies on the used method's results in the
  listener you provide to it. In addition, when passing along
  :java:ref:`NetworkResponse` errors from the used method, you may need to
  change the type of response when passing it along. Use the constructor that
  takes another response object, as this discards any payload (which is not
  needed for errors) and preserves the authentication failure status.
* When using an API method in Activities or non-API classes, create a
  ``RequestQueue`` for the entire activity and pass it to all calls to API
  methods. In each call, pass along a listener that describes what to do with
  the response.

-----
Other
-----

First Activity
==============
When the application starts from scratch (i.e. is not being launched by
restoring a previous state), the :java:ref:`ApplicationStart` activity is
loaded. This performs initialization for the app (e.g. Crashlytics) and
redirects the user to either :java:ref:`TimelineActivity`,
:java:ref:`OnboardActivity`, or :java:ref:`ExploreBubblesOpenGLActivity` based
on whether they have logged in and whether they have a selected network.

Managing Formatted Text
=======================
In cases where the user can create formatted text using inline markup (i.e.
bold, italics, and hyperlinks), :java:ref:`FormatManager` handles the
markup.

Handling Redirections
=====================
In a few cases, a parent activity needs to launch a child activity while also
directing the child to launch a particular grand-child activity. For example,
when :java:ref:`SettingsActivity` launches :java:ref:`OnboardActivity`, the user
should be sent back to SettingsActivity at the end. If
:java:ref:`ApplicationStart` is instead launching :java:ref:`OnboardActivity`,
the user should next be sent on to :java:ref:`LoginActivity`. This is handled
by :java:ref:`Redirection`.
