=================
Structure of Code
=================

--------------
User Interface
--------------

-----------
Data Models
-----------

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
