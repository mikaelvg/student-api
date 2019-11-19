# Student API Java Application
**Highlights**
  - Uses Springboot Framework
  - Profile-based deployment. Ex. H2 database for Local and MySQL database for SIT.
  - Data-Driven Testing - Change the data to change the test scenario.
  - 80% plus Automated test coverage with Low Line-Of-Codes.
  - BlackBox (Standalone) Testing with no test mocks.
  - Repeatable test
  
**Test the app**
URLs: 
- Home: http://tg-student-api-03aba33eb900125e.elb.us-east-1.amazonaws.com/
- All Student: http://tg-student-api-03aba33eb900125e.elb.us-east-1.amazonaws.com/api/student
- Student by ID: http://tg-student-api-03aba33eb900125e.elb.us-east-1.amazonaws.com/api/student/1
- List of students per class: http://tg-student-api-03aba33eb900125e.elb.us-east-1.amazonaws.com/api/fetchstudent?fieldName=class1&value=A1
- Search by Firstname: http://tg-student-api-03aba33eb900125e.elb.us-east-1.amazonaws.com/api/fetchstudent?fieldName=firstName&value=Mikael
- To test the Create-Update-Delete. Using your favorite GUI i.e. intellij, Go to StudentApplicationTests.java, right click desired method, then select **Run <method name>** 

**How to run locally**
- To run End-to-End (Integration) Testing
```sh
$ mvn clean test
```
- Run locally and access via http://localhost:9080
```sh
$ mvn clean spring-boot:run
```
# SonarQube Analytics
URL: https://sonarcloud.io/dashboard?id=mikaelvg_student-api
publicly accessible

# Jenkins
**Highlights**
- Uses Jenkins Pipeline
- Github WebHook
- Runs Code Scan and uploads results to SonarCloud
- Uploads docker image to docker hub.

**Accesss**
URL: http://54.90.217.130:8080/job/student-api/
Account: admin/M1kael

# Amazon Web Services
**Highlights**
- Network load Balancer enabled.
- Auto Scalling enabled.
- Runs on high Availabity Elastic Container Services (ECS).
- Pulls the latest docker images from dockerhub.

**Accesss**
https://089131161357.signin.aws.amazon.com/console
Account: demo/M1kael
Region: N. Virginia