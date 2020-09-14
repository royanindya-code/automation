/***** Installation Instruction ****/
1. Java 8 should be installed in the system
2. Maven should be installed in the system
3. Jre/Maven should be set to the system path
4. To check if java installed use this command - java -version and javac -version
5. to check maven isntalled in the system use this command - mvn -version
6. Clone the project from git repo using - git clone https://github.com/RoyAnindya/automation.git command
7. Alternate downloading option is to download as a zip format.

Please find the steps to run on eclipse - 
Prerequisite - 
1. Eclipse should be installed.
2. testng extension should be added to eclipse
3. Create a workspace in eclipse and import the project.
4. If there is any build error. Please do a clean and then update maven project.

Run from eclipse - 
1. Go to src/test/java/TemperatureTest.java and right click testTemperature method and run as testng. 
2. Test will automatically run and after that check the results in test-output/emailable-report.html file.
3. All the locators are declared in yaml file insied Resources/Locators folder.
4. The excel test data sheet for variance is present inside Resources/TestData folder.
5. All the drivers are present in Resources/Web folder.

Limitation -
1. Since I am using mac,only chromedriver and firefox driver for mac has been added to the framework.

To overcome this limitation this test can also work on selenium grid, if you have grid setup
in your machine then you can pass the grid url in *gridUrl* inside plugins. Ex - 
<gridUrl>localhost</gridUrl> .

Note - if gridUrl is set to localost then it will run on local machine. Otherwise, it will be
running on selenium grid. As of now, Only chrome and firefox browser is being considered for grid.

Run this project from command line - 
mvn clean test -Dparallel=methods -DthreadCount=10 -Dbrowser=chrome

if you want to run on firefox then use this command - 
mvn clean test -Dparallel=methods -DthreadCount=10 -Dbrowser=firefox

After running this command , test results can be verified in target/surefire-reports/emailable-report.html file.
