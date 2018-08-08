=====================
User Interaction Flow
=====================

The below diagram illustrates roughly how users move through the app and
between different activities.

.. code-block:: plain

                                      Launch App
                                          |
                                   ApplicationStart
                                  /       |        \
                                 /        |         \
                                /         |          \
                not logged in  /          |           \ no selected network
                              /           | has        \
                             /            | selected    \
                            /             | network      \
                    OnboardActivity       |           ExploreBubblesOpenGL
                           |              |                     |
                    LoginActivity         +---------------------+
                           |              |
                ExploreBubblesOpenGL------+
                                          |
                        TimelineActivity extends DrawerActivity
                       /     |       |                 |
                      /  View Users  +    +-----+------+------------+-------+
                     /               |    |     |      |            |       |
                    /       +--------+ Settings |  Find Network   Explore   |
  FloatingActionButton      |        |          |      |                    |
    (Create Button)         |        |          |  TimelineActivity       Logout
    /              \  View Post   View Event    |                           |
   |               |     |             |        |                           |
   |               |     +---------+---+    +---+-------------+       OnboardActivity
   |               |               |        |                 |
   |  CreateEventActivity      Make Reply   |               AboutActivity
   |                                        |
   CreatePostActivity                      Help
                                            |
                                     OnboardActivity
