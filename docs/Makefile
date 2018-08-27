# Minimal makefile for Sphinx documentation
#

# You can set these variables from the command line.
SPHINXOPTS    =
SPHINXBUILD   = sphinx-build
SPHINXPROJ    = CultureMeshAndroid
SOURCEDIR     = source
BUILDDIR      = build

# Constants
ANDROID_PATH = ..
ANDROID_DOCS_PATH = source/android

# Put it first so that "make" without argument is like "make help".
help:
	@$(SPHINXBUILD) -M help "$(SOURCEDIR)" "$(BUILDDIR)" $(SPHINXOPTS) $(O)

.PHONY: help Makefile

# Use Android Studio's javadoc to create javadoc HTML files in source/_static/API-JavaDoc
javadoc-traditional:
	@echo "Generating Traditional Javadoc"
	@rm -rf source/_static/API-JavaDoc/*
	@"/Applications/Android Studio.app/Contents/jre/jdk/Contents/Home/bin/javadoc" -private -quiet -splitindex -d source/_static/API-JavaDoc -sourcepath $(ANDROID_PATH)/app/src/main/java/ -bootclasspath /Users/cs/Library/Android/sdk/platforms/android-27/android.jar -Xdoclint:none com.culturemesh

# Use javasphinx-apidoc to create javadoc .rst files in the docs directory
javadoc-sphinx:
	@echo "Generating Sphinx Form of Javadoc"
	@rm -rf $(ANDROID_DOCS_PATH)/javadoc/org
	@rm -f $(ANDROID_DOCS_PATH)/javadoc/packages.rst
	@javasphinx-apidoc -c cache/javasphinx -u -p html5lib -o $(ANDROID_DOCS_PATH)/javadoc/ $(ANDROID_PATH)/app/src/main/java/org/codethechange/culturemesh/

# Generate Javadoc via: Android Studio's javadoc tool and javasphinx-apidoc
javadoc: javadoc-traditional javadoc-sphinx
	@echo "Generating all Javadoc Documentation"

# Generate all documentation .rst files (and Javadoc HTML), but don't run sphinx
docs: javadoc
	@echo "Generating All Documentation Source Files"

# Generate all documentation .rst files, but don't run sphinx
sphinx-docs: javadoc-sphinx
	@echo "Generating Sphinx Documentation Files"

# Catch-all target: route all unknown targets to Sphinx using the new
# "make mode" option.  $(O) is meant as a shortcut for $(SPHINXOPTS).
# Includes both javadoc and swagger as pre-requisites so they are also run with all unknown targets
%: Makefile javadoc
	@$(SPHINXBUILD) -M $@ "$(SOURCEDIR)" "$(BUILDDIR)" $(SPHINXOPTS) $(O)
