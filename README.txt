CSC2002: Assignment 3 
DPLJER001
September 2018

NB! Before attempting to run any compiled java code in the bin folder via the make file, you make need to re-compile all the java source files as they have been compiled using the most recent JRE (10). If so, clean the bin folder and recompile the source code with:

	$ make clean
	$ make 

a. This folder contains all submission content for A3 in the following :

src: 		source code for solution and experiments

bin: 		compiled java code

data: 		data files for parallel programs

testResults: 	results from speed-up experiments on different architectures (the treeExperiments program also writes results here)

Git Files	the git log record and git repository (I could not get git archive to work, my repo is on bitbucket so I am happy to show that as evidence of my work if need be)

Report.pdf: 	written report with all graphed-results from experiments on different architectures 



b. To run the solution code do the following:

	1. open the make file and under the command "run_main" fill in the input and output file names (ensure the paths are relative to the make file):

		|| run_main:
		|| 	java -cp $(BINDIR) treeSumParallel "<input file name>" "<output file name>" 

	2. Type the following command from the root folder (the one where the make file is):

		$ make run_main

	3. Read the output on the console, the average time in seconds will be reported back to you, along with the value of the sequential cutoff

	(4. If you want to run the program sequentially, you can set the value of the SEQUENTIAL_CUTOFF variable in the Constants.java file in the src directory to 1000001, recompile and run the program again, although evidence of speed up can be seen by running the experiments code - see c. below)



c. To run the experiment code, type the following command from the root folder (where the make file is):

	$ make run_experiments

	The output will be written to the testResults folder (see report for more details)

Thanks,
Jeremy 
