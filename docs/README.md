# CultureMesh Documentation
This repository stores documentation for CultureMesh's Android app and API.

## Dependencies
The following dependencies may be needed to successfully generate the
documentation using this workflow:
* `git`
* Python packages detailed in the pip requirements format in `requirements.txt`

## Usage

### Generate HTML Documentation
To generate documentation, run `make html` from the `docs` directory. This will
generate HTML documentation in `docs/build/html`, with a homepage at
`docs/build/html/index.html`.

### Generate Sphinx Source Documentation Files
Run `make sphinx-docs` to generate the `.rst` source files for `sphinx`. This
is designed to create the files to be used with services like
[ReadTheDocs](https://docs.readthedocs.io/).

## Definitions
These definitions are used throughout this repository.
* `automatic documentation`: Documentation generated from Javadoc and docstrings
in the code repositories

## How Documentation is Generated
The `make` command follows the instructions in `Makefile`, which involve the
following steps:

1. Generate automatic documentation. These files are created by converting code
comments into reStructuredText (`.rst`) files using tools like `javasphinx-apidoc`.
2. Generate non-reStructuredText documentation. This only occurs for Javadoc,
since the output from `javasphinx-apidoc` is in some cases less useful than
traditional Javadoc. The `javadoc` command is run directly to generate HTML files
in the `docs/source/_static` directory. These HTML files are then linked-to from
within the reStructredText documentation.
3. Compile all generated documentation into beautiful HTML using `sphinx`.
