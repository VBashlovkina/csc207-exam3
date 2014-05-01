# A quick-and-dirty makefile for making the tarball for the exam

# +----------+--------------------------------------------------------
# | Settings |
# +----------+

# The user.	Please replace with your username.
USER=rebelsky

# The files that go in the tarball.	If you add things to the repo,
# you should update this.
FILES= \
	$(USER)/Makefile \
	$(USER)/README.md \
	$(USER)/.git \
	$(USER)/.gitignore \
	$(USER)/Problem1 \
	$(USER)/Problems23/.classpath \
	$(USER)/Problems23/.project \
	$(USER)/Problems23/src/BST.java \
	$(USER)/Problems23/src/BSTExpt.java \
	$(USER)/Problems23/src/BSTTrace.java \
	$(USER)/Problems23/src/Dictionary.java \
	$(USER)/Problems23/src/RandomBSTTests.java \
	$(USER)/Problems23/src/SystematicBSTRemoveTests.java \
	$(USER)/Problem4/.classpath \
	$(USER)/Problem4/.project \
	$(USER)/Problem4/src/AssociationList.java \
	$(USER)/Problem4/src/AssociationListTest.java \
	$(USER)/Problem4/src/ChainedHashTable.java \
	$(USER)/Problem4/src/ChainedHashTableTest.java \
	$(USER)/Problem4/src/Dictionary.java \
	$(USER)/Problem4/src/DictionaryFactory.java \
	$(USER)/Problem4/src/DictionaryIteratorTests.java \
	$(USER)/Problem4/src/OpenHashTable.java \
	$(USER)/Problem4/src/OpenHashTableTest.java

# +------------------+------------------------------------------------
# | Standard Targets |
# +------------------+

.PHONY: default
default: tarball

.PHONY: clean
clean: 
	rm -rf 

# +----------------+--------------------------------------------------
# | Custom Targets |
# +----------------+

# Create the standard tarball
.PHONY: tarball
tarball: clean
	tar -C.. -cvzf $(USER).tar.gz $(FILES)
