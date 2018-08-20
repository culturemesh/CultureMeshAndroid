============
Contributing
============

Thank you for your interest in contributing to CultureMesh Android!
Here are a few steps to get you up and running:

#. Follow the instructions in :doc:`index` to get set up with the
   code base and Android Studio.
#. Open an issue on
   `GitHub <https://github.com/DrewGregory/CultureMeshAndroid>`_
   describing the changes you'd like to make. This is important
   beacuse your idea might already be in development or might not
   match the direction we are planning to take the app in. Reaching
   out to describe your proposal first will help avoid unnecessary
   work. You should also offer to work on it so people know not to
   do it themselves.
#. If your idea is accepted, start working on your idea! You might
   need to ask for suggestions or discuss implementation details in
   the issue first.
#. If you don't have commit access, you'll need to fork the
   repository and then clone your copy instead of the main fork.
#. Create a new branch for your changes:

   .. code-block:: console

     $ git checkout -b your_branch_name

#. Make your changes. Please divide up your work into chunks, each
   of which could be un-done without breaking the app's functionality.
   Make each chunk a commit. Please include comments and documentation
   updates as needed in your changes, preferably in the commit which
   necessitated them. The commit message should follow
   the below style (inspired by `Pro-Git <https://git-scm.com/doc>`_,
   page 127):

   .. code-block:: plain

     Summary on one line and under 70 characters

     After a blank line, you can have paragraphs as needed to more
     fully detail your changes. Wrap them at ~72 lines (no more than
     80) for people viewing it from a command line interface.

     Separate paragraphs with a single line.
       - For bullet points, use hyphens or asterisks
       - You don't need a blank line between bullet points, but you
         should indent multiple lines to create a block of text.

     In your message, describe both what you changed at a high level
     and, more importantly, why you changed it. The rationale is
     important to include because it might not be clear from your
     code changes alone.

#. Push your changes:

   .. code-block:: console

     $ git push --set-upstream origin your_branch_name

#. Create a pull request describing your changes and why they were
   made. Use the ``develop`` branch as the base for your pull request.
#. Before your pull request can be accepted, it must be reviewed.
   Your reviewer may suggest changes, which you should then make or
   explain why they aren't needed. This is a way to create dialogue
   about changes, which generally enhances code quality.
#. Once your pull request is accepted, you can delete your branch.

.. warning::

  There are currently no automated tests for this project.
  Unfortunately, this means you will have to test manually to ensure
  your changes don't break anything.
