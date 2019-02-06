# Tree Sum / Parallel Assignment 
# Jeremy du Plessis
# September 2018

JC = /usr/bin/javac
JFLAGS = -g -d

.SUFFIXES = .java .class
SRCDIR=src
BINDIR=bin

$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JC) $(JFLAGS) $(BINDIR) -cp $(BINDIR) $<

CLASSES = 	tree.class bundle.class \
		Constants.class treeUtils.class \
		SumArray.class treeSumParallel.class \
		treeExperinments.class 	
	
		
CLASS_FILES = $(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

run_main:
	java -cp $(BINDIR) treeSumParallel "data/main_input.txt" "data/main_out.txt"

run_experiments:
	java -cp $(BINDIR) treeExperinments "250000"
	java -cp $(BINDIR) treeExperinments "500000"
	java -cp $(BINDIR) treeExperinments "750000"
	java -cp $(BINDIR) treeExperinments "1000000"

clean:
	rm $(BINDIR)/*.class
