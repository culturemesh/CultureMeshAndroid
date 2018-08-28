.. java:import:: android.content Intent

Redirection
===========

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public class Redirection

   Classes that extend this one can be sent information when launched regarding where the user should be directed next.

Fields
------
LAUNCH_ON_FINISH_EXTRA
^^^^^^^^^^^^^^^^^^^^^^

.. java:field:: static final String LAUNCH_ON_FINISH_EXTRA
   :outertype: Redirection

   Key in \ :java:ref:`android.content.Intent`\ 's extras whose argument specifies the Class of the Activity to launch when finishing

   **See also:** :java:ref:`Intent.getExtras()`

PASS_ON_FINISH_EXTRA
^^^^^^^^^^^^^^^^^^^^

.. java:field:: static final String PASS_ON_FINISH_EXTRA
   :outertype: Redirection

   Key in \ :java:ref:`android.content.Intent`\ 's extras whose argument specifies a \ :java:ref:`android.os.Bundle`\  whose contents will be passed as extras via the Intent called on finishing

