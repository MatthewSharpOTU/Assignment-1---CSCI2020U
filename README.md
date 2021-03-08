# Assignment-1---CSCI2020U
Assignment 1 Repository for System Development and Integration - CSCI2020U. <br />
Group Members: `Matthew Sharp - 100748071` and `Andy Wang - 100751519`

The program will be able to determine whether an email is detected as a spam message or a legitimate message (denoted by the phrase "ham"). The program will first train to analyze which emails are spam or ham from respective predefined lists that the user inputs as a relative path. Common words and phrases in these documents will be labelled as spam or ham, for the program to then identify whether an email in the testing phase is a spam or ham message. The program will then output the spam probabilty of each message in a list of emails and then display the accuracy (percentage of correct guesses) and precision (ratio to correct positive to spam guesses).

![Alt text](/OpenMenu.png)

![Alt text](/OutputData.png)

Improvements:
- UI: Able to select a directory of test code.
- Model: Rather than saving a word to a ham or spam map. Instead the frequency of valid words (which are words extracted from sentences that only comprises upper case and lower case letters) in a file are stored by a custom class that holds the String to the directory of the file. The valid words are used to calculate the percent whether it is a ham or a spam and it seems to be more accurate.

Instructions:
- Using Intellij to run program:

- Clone project to desired directory if getting files repository from GitHub
- Extract files to a desired directory if getting files from zip.

- now open up Intellij and click on the top left `File -> Open`
- navigate to the cloned repository

- Try pressing run to attempt to compile the project in Intellij

- If it a window shows up. grate

- If an error appeared in the output window:
1. Navigate to `Files -> Project Structures...` and see and make sure that the right sdk version 11+ and jdk version 11+ are installed an applied
2. Navigate to `Run -> Edit Configurations...` if you do not see an Application/Main then press the + symbol Application. Under build and run select the correct jdk file and version then press `Modify Options -> Add VM Options` and type this into the VM options input bar: --module-path "The\Path\To\JDK\lib;out\production" --add-modules=javafx.controls,javafx.fxml

- the window to the email checker should open.

References:
[1] ​https://en.wikipedia.org/wiki/Bag-of-words_model
[2] ​https://en.wikipedia.org/wiki/Naive_Bayes_spam_filtering
