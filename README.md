# SAP HANA with Vaadin 8 & Spring Boot

Simple demo-app for using SAP HANA with Vaadin 8 & Spring Boot

(Documentation coming, please wait)

JVM used is SAP JVM 8, see https://tools.hana.ondemand.com/#cloud

To run local:
mvn spring-boot:run -Drun.profiles=dev,standalone

To build for SAP cloud:
spring-boot:run -Drun.profiles=dev,standalone
(makes a deployable war)