1. CSC 242

2. Project 2 (Model Checking and Satisfiability Testing)

3. Jiayi Hao 31727716 (jhao3@u.rochester.edu)

4. Group members
Shiyu Liu 31726955 (sliu89@u.rochester.edu)
Erjia Meng 31727297 (emeng2@u.rochester.edu)

5. Running Instructions
(1) IMPORTANT!! Please make sure the following .cnf files are in the same folder as the code
    nqueen_4.cnf
    nqueen_8.cnf
    nqueen_12.cnf
    quinn.cnf
    par8-1-c.cnf
    (We included these files in our submission. The codes need these files to run)
(2) First, start command-line at the folder where the source codes located.
(3) Compile the java code as below: 


javac *.java


(4) To test Part 2 - Model Checking as below:


java ModelChecking


The program will ask about which problems of Part 2 you want to test (1,2,3) which are corresponding
to the PQ, Wumpus, and Unicorn as required. 

Expect results as below (copied from the command-line):
----------------------------------------------------------------------------------------------------
    D:\JerryHao\Documents\UOR\Sophomore\DSCC242\Project\project2>javac *.java

    D:\JerryHao\Documents\UOR\Sophomore\DSCC242\Project\project2>java ModelChecking
    Choose problem from {1,2,3}. Type -1 to exit.
    1
    Clause:
    {1},{-1,2}
    KB entails Q is true

    Choose problem from {1,2,3}. Type -1 to exit.
    2
    The agent starts at [1, 1]. R1, R2, R3, R7 added to knowledge base.
    Examine queries.
    KB entails ~P1,2 is true
    KB entails ~P2,1 is true
    KB entails P2,2 is false
    KB entails ~P2,2 is false

    The agent moves to [2, 1]. R5 added.
    Examine queries.
    KB entails P2,2 V P3,1 is true
    KB entails P2,2 is false
    KB entails ~P2,2 is false
    KB entails P3,1 is false
    KB entails ~P3,1 is false

    The agent moves to [1, 2]. R6 added.
    Examine queries.
    KB entails ~P2,2 is true
    KB entails P3,1 is true

    Choose problem from {1,2,3}. Type -1 to exit.
    3
    testQ3
    Literals:
    1 - mythical    2 - immortal    3 - mammal      4 - horned      5 - magical
    Clauses:
    {-1,2},{1,-2},{1,3},{-2,4},{-3,4},{-4,5}

    We can't prove that the unicorn is mythical.
    We can prove that the unicorn is magical.
    We can prove that the unicorn is horned.

    Choose problem from {1,2,3}. Type -1 to exit.
    -1
---------------------------------------------------------------------------------------------------

(4) To test Part 3 - Satisfiability Testing as below:


java SatisfiabilityChecking


The program will ask about which problems of Part 3 you want to test (1,2,3) which are corresponding
to the Simple, N-Queens, and CNF file as required. See the folloing details:
- For N-Queens Probelm, you will be asked to choose from 4, 8 and 12 Queens. (16 is not supported
since it takes sooo long to run). Here, for 12 Queens problem, WalkSAT algorithm is used instead by
default (with p=0.175). It largely improved the speed and can find the assignment almost all the time.
- For CNF file Problem, you can choose between quinn.cnf (1) and par8-1-c.cnf (2). Likewise, for 
par8-1-c.cnf, WalkSAT is used by default (with p=0.4). It can find the assignment in 5s on average.

Then, you will be asked whether you want to use your own number of MAX-TRIES and MAX-FLIPS. If you
input Y, the program will ask the value individually. Otherwise, with N, it will use the default
value, which depends on different questions. We suggest to use the default value since that is the
most safe value we picked after testing. If you choose too small value for them, the program might
incorrectly report unsatisfiable for satisfiable knowledge base.

Expect results as below (copied from the command-line):
----------------------------------------------------------------------------------------------------
    D:\JerryHao\Documents\UOR\Sophomore\DSCC242\Project\project2>javac *.java

    D:\JerryHao\Documents\UOR\Sophomore\DSCC242\Project\project2>java SatisfiabilityChecking
    Choose problem from {1,2,3}. Type -1 to exit.
    1
    Do you want to set MAX-TRIES and MAX-FLIPS yourself? Answer: Y/N. (Suggested Default: 5, 10)
    N
    [true, false, false, true]

    Choose problem from {1,2,3}. Type -1 to exit.
    2
    Enter number of queens from {4,8,12}.
    12
    Do you want to set MAX-TRIES and MAX-FLIPS yourself? Answer: Y/N. (Suggested Default: 200, 100)
    This might take some time to run (or even turn out unsatisfiable) if you're very unlucky
    N
    |_|_|_|_|_|_|_|X|_|_|_|_|
    |_|_|_|_|X|_|_|_|_|_|_|_|
    |X|_|_|_|_|_|_|_|_|_|_|_|
    |_|_|_|_|_|_|_|_|_|X|_|_|
    |_|_|_|_|_|X|_|_|_|_|_|_|
    |_|X|_|_|_|_|_|_|_|_|_|_|
    |_|_|_|_|_|_|_|_|X|_|_|_|
    |_|_|_|_|_|_|_|_|_|_|_|X|
    |_|_|_|X|_|_|_|_|_|_|_|_|
    |_|_|_|_|_|_|X|_|_|_|_|_|
    |_|_|_|_|_|_|_|_|_|_|X|_|
    |_|_|X|_|_|_|_|_|_|_|_|_|
    Satisfiable. See the truth table below:
    [false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false]

    Choose problem from {1,2,3}. Type -1 to exit.
    3
    Choose the problem you want to run.
    1. quinn.cnf
    2. par8-1-c.cnf
    1
    Do you want to set MAX-TRIES and MAX-FLIPS yourself? Answer: Y/N. (Suggested Default: 50, 50)
    N
    [true, false, true, false, true, true, true, true, true, false, true, true, true, false, false, true]

    Choose problem from {1,2,3}. Type -1 to exit.
    3
    Choose the problem you want to run.
    1. quinn.cnf
    2. par8-1-c.cnf
    2
    Do you want to set MAX-TRIES and MAX-FLIPS yourself? Answer: Y/N. (Suggested Default: 500, 500)
    N
    [true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true, true, false, true, true, false, true, true, true, true, true, false, true, true, true, true, true, false, true, true, true, true, true, false, true, false, true, true, true, false]

    Choose problem from {1,2,3}. Type -1 to exit.
    -1
----------------------------------------------------------------------------------------------------

