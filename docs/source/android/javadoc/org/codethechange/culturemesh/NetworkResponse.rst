.. java:import:: android.app Dialog

.. java:import:: android.content Context

.. java:import:: android.content DialogInterface

.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

.. java:import:: android.support.v7.app AlertDialog

.. java:import:: android.util Log

NetworkResponse
===============

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class NetworkResponse<E>

   Class to store responses after attempting networking tasks

Constructors
------------
NetworkResponse
^^^^^^^^^^^^^^^

.. java:constructor:: public NetworkResponse(NetworkResponse<?> toConvert)
   :outertype: NetworkResponse

   Create a new NetworkResponse of the type designated in \ ``<>``\  from another NetworkResponse of any other type. Any payload in the source object will not be transferred to the created one. All other fields are copied.

   :param toConvert: Source to create new object from. All properties except payload will be copied.

NetworkResponse
^^^^^^^^^^^^^^^

.. java:constructor:: public NetworkResponse(boolean inFail)
   :outertype: NetworkResponse

   Constructor that creates a generic message based on "inFail"

   :param inFail: Failure state provided by user (true if failed)

NetworkResponse
^^^^^^^^^^^^^^^

.. java:constructor:: public NetworkResponse(boolean inFail, int inMessageID)
   :outertype: NetworkResponse

   Constructor that sets message and failures state based on arguments

   :param inFail: Failure state provided by user (true if failed)
   :param inMessageID: ID for string resource containing message

NetworkResponse
^^^^^^^^^^^^^^^

.. java:constructor:: public NetworkResponse(E inPayload)
   :outertype: NetworkResponse

   Constructor that stores a payload and sets the failure state to false

   :param inPayload: Payload returned by networking request

NetworkResponse
^^^^^^^^^^^^^^^

.. java:constructor:: public NetworkResponse(boolean inFail, E inPayload)
   :outertype: NetworkResponse

   Constructor that both stores a payload and sets the failure state from parameters

   :param inFail: Whether or not the network operation failed
   :param inPayload: Payload returned by networking request

NetworkResponse
^^^^^^^^^^^^^^^

.. java:constructor:: public NetworkResponse(boolean inFail, E inPayload, int messageID)
   :outertype: NetworkResponse

   Constructor that both stores a payload and sets the failure state from parameters

   :param inFail: Whether or not the network operation failed
   :param inPayload: Payload returned by networking request

Methods
-------
fail
^^^^

.. java:method:: public boolean fail()
   :outertype: NetworkResponse

   Check whether the network request failed

   :return: true if the request failed, false if it succeeded

genErrorDialog
^^^^^^^^^^^^^^

.. java:method:: public static AlertDialog genErrorDialog(Context context, int messageID)
   :outertype: NetworkResponse

   Get an error dialog that can be displayed to the user

   :param context: Context upon which to display error dialog (Should be \ ``someClass.this``\ )
   :param messageID: String resource ID of message to display
   :return: \ :java:ref:`AlertDialog`\  with specified alert message.

genErrorDialog
^^^^^^^^^^^^^^

.. java:method:: public static AlertDialog genErrorDialog(Context context, int messageID, DialogTapListener listener)
   :outertype: NetworkResponse

   Get an error dialog that can be displayed to the user

   :param context: Context upon which to display error dialog (Should be \ ``someClass.this``\ )
   :param messageID: String resource ID of message to display
   :param listener: A \ :java:ref:`DialogTapListener`\  for when the user dismisses the dialog.
   :return: \ :java:ref:`AlertDialog`\  with specified alert message.

genErrorDialog
^^^^^^^^^^^^^^

.. java:method:: public static AlertDialog genErrorDialog(Context context, int messageID, boolean authFail, DialogTapListener mListener)
   :outertype: NetworkResponse

   Get an error dialog that can be displayed to the user

   :param context: Context upon which to display error dialog (Should be \ ``someClass.this``\ )
   :param messageID: String resource ID of message to display
   :param authFail: Whether or not the user should be directed to \ :java:ref:`LoginActivity`\  upon dismissing the dialog
   :param mListener: A \ :java:ref:`DialogTapListener`\  for when the user dismisses the dialog.
   :return: \ :java:ref:`AlertDialog`\  with specified alert message and which directs the user to \ :java:ref:`LoginActivity`\  upon dismissal if \ ``authFail``\  is true.

genSuccessDialog
^^^^^^^^^^^^^^^^

.. java:method:: public static AlertDialog genSuccessDialog(Context context, int messageID)
   :outertype: NetworkResponse

   Get a confirmation dialog that can be displayed to the user to reflect a successful operation

   :param context: Context upon which to display dialog (Should be \ ``someClass.this``\ )
   :param messageID: String resource ID of message to display
   :return: \ :java:ref:`AlertDialog`\  with specified alert message

getAuthFailed
^^^^^^^^^^^^^

.. java:method:: public static NetworkResponse<API.Get.LoginResponse> getAuthFailed(int messageID)
   :outertype: NetworkResponse

   Get a NetworkResponse object with \ :java:ref:`NetworkResponse.isAuthFailed`\  is \ ``true``\ . This means that when the user dismisses the error dialog generated by \ :java:ref:`NetworkResponse.getErrorDialog(Context,DialogTapListener)`\  or \ :java:ref:`NetworkResponse.showErrorDialog(Context)`\ , \ :java:ref:`LoginActivity`\  will be launched.

   :param messageID: String reference to the message describing the error. Will be shown to user
   :return: NetworkResponse object to describe an authentication failure.

getErrorDialog
^^^^^^^^^^^^^^

.. java:method:: public AlertDialog getErrorDialog(Context context, DialogTapListener listener)
   :outertype: NetworkResponse

   Get an error dialog that can be displayed to show message from messageID to user

   :param context: Context upon which to display error dialog (Should be \ ``someClass.this``\ )
   :param listener: A \ :java:ref:`DialogTapListener`\  to be called when they dismiss the dialog.
   :return: Dialog that can be shown

getMessageID
^^^^^^^^^^^^

.. java:method:: public int getMessageID()
   :outertype: NetworkResponse

   Get the resource ID of the message to display to the user

   :return: Resource ID of message

getPayload
^^^^^^^^^^

.. java:method:: public E getPayload()
   :outertype: NetworkResponse

   Get the payload returned by the network operation

   :return: Payload returned by network operation

isAuthFailed
^^^^^^^^^^^^

.. java:method:: public boolean isAuthFailed()
   :outertype: NetworkResponse

   Get whether the current object represents a failed authentication

   :return: \ ``true``\  if object represents an authentication failure, \ ``false``\  otherwise

setAuthFailed
^^^^^^^^^^^^^

.. java:method:: public void setAuthFailed(boolean isAuthFailed)
   :outertype: NetworkResponse

   Set whether the current object represents a failed authentication

   :param isAuthFailed: \ ``true``\  if object represents an authentication failure, \ ``false``\  otherwise

showErrorDialog
^^^^^^^^^^^^^^^

.. java:method:: public void showErrorDialog(Context context, DialogTapListener listener)
   :outertype: NetworkResponse

   Show an error dialog that can be displayed to show message from messageID to user

   :param context: Context upon which to display error dialog
   :param listener: A \ :java:ref:`DialogTapListener`\  object which allows you control behavior after they dismiss the dialog.

showErrorDialog
^^^^^^^^^^^^^^^

.. java:method:: public void showErrorDialog(Context context)
   :outertype: NetworkResponse

   Show an error dialog that can be displayed to show message from messageID to user

   :param context: Context upon which to display error dialog

toString
^^^^^^^^

.. java:method:: public String toString()
   :outertype: NetworkResponse

   Get a String representation of the object that conveys the current state of all instance fields

   :return: String representation of the form \ ``NetworkResponse<?>[field1=value1, ...]``\

