# SAP HANA with Vaadin 8 & Spring Boot

Simple demo-app for using SAP HANA with Vaadin 8 & Spring Boot

This is actually a mash-up from different projects:
https://github.com/SAP/cloud-spring-boot-sample - how to use Spring boot with SAP HANA Cloud Platform

https://github.com/spring-guides/gs-crud-with-vaadin - Building a crud with Spring + Vaadin

(+ some eye candy css, for sake of fun)

## Prerequisites
1. Create developer account [at sap cloudplatform page](https://cloudplatform.sap.com/try.html)
  1. ![1-signup](https://cloud.githubusercontent.com/assets/19262966/24752659/99101b2e-1ad7-11e7-9fb8-a938c8b9aee1.jpg)
2. After successful registration, login
3. In SAP Cloud Platform Cockpit, create a new database:
   * ![2-cockpit](https://cloud.githubusercontent.com/assets/19262966/24752667/a09b07c8-1ad7-11e7-9f03-a7a52478ad67.jpg)
   * Choose: Persistence/Databases & Schemas
   * Click new
   * ![3-createdb](https://cloud.githubusercontent.com/assets/19262966/24752674/a62f9046-1ad7-11e7-873e-7902a46f2692.jpg)
   * Choose db system: HANA MDC, configure User for Shine true
   * Fill out rest to your liking (just remember those passwords :) )
   * Save
   * (Database creation takes quite some time, don't worry)
4. Once database is up, navigate to that db and from that view, click "SAP HANA Web-based Development Workbench"
  1. ![4-database-open](https://cloud.githubusercontent.com/assets/19262966/24753100/6ca1515a-1ad9-11e7-9269-e65cf9471815.jpg)
5. If you're prompted for username-password use the ones you set in your shine -user
6. Choose "Catalog" (There should be a screen with options: Editor, Catalog, Security, Traces)
  1. ![5-development-workbench](https://cloud.githubusercontent.com/assets/19262966/24752692/baadb6ce-1ad7-11e7-8606-6bcdbee8c8b4.jpg)
7. Expand Catalog and from under that you should have database (okay, not a db but catalog or schema or whatever) named as the user you made (default user was "shine", I changed that as "test", so my db is named "TEST")
  1. ![6-catalog](https://cloud.githubusercontent.com/assets/19262966/24752700/c2b0da0e-1ad7-11e7-95f6-97f9d179d375.jpg)
8. Open SQL -console and create customer table*
9. Get SAP JVM 8, see see https://tools.hana.ondemand.com/#cloud
10. Set your project to use that JVM

>You actually **don't** have to create or use SHINE user. It's just easier to tag that, for it creates a user for database. Plus you get different SHINE test/learning stuff

## Customer table
```SQL
CREATE COLUMN TABLE "TEST"."CUSTOMER"
    ("CUST_UUID" NVARCHAR(36) NOT NULL ,
	 "FIRSTNAME" NVARCHAR(50),
	 "LASTNAME" NVARCHAR(50),
	 "IMG_URL" NVARCHAR(255),
	 PRIMARY KEY ("CUST_UUID"))
```

Note that the "TEST" must changed with your own shine-user name. It defaulted as "shine", so if it was left as is use "SHINE"....

## Running the app

### To run local
```
mvn spring-boot:run -Drun.profiles=dev,standalone
```

### Run in SAP cloud
Build deployable war:
```
mvn -P neo clean package install
```

In SAP Cloud Platform Cockpit:

First time:

1. Choose Applications / Java Applications / Deploy Application
  1. Choose war-file (in target folder, named sapvaadinspring.war)
  2. Run-time Name: Java Web Tomcat 8
  3. JVM Version: JRE 8
  4. JVM Arguments: -Dspring.profiles.active=neo
  5. Deploy (But do not start the application)
  6. ![7-deploy-application](https://cloud.githubusercontent.com/assets/19262966/24752709/c995e2ba-1ad7-11e7-966b-400226c87f9b.jpg)
2. Choose you database
3. Choose "Data Source Bindings"
  1. ![8-data-binding](https://cloud.githubusercontent.com/assets/19262966/24752713/d00b7560-1ad7-11e7-8f2c-c5c7f18dd3d9.jpg)
4. Fill in username and password and save
5. Start your application

If you're only updating (meaning you've done the first time already), then just update your applications by giving a new war-file and restart application.

### The application screenshot

![9-final-version](https://cloud.githubusercontent.com/assets/19262966/24752718/d7e2c400-1ad7-11e7-991b-ae3421b93b60.jpg)
