# ChecklistAPIs
Checklist management APIs using Spring Boot stack.

1.Spring MVC framework for REST controllers</BR>
2.Spring Core</BR>
3.Spring Data/JPA/Hibernate</BR>
5.Spring Transactions</BR>
4.H2 Database as data store for both PROD/TEST datasource.</BR>

Steps to run services</BR>
==================</BR>
1.Download project source and import to Intellij/Eclipse </BR>
2.Run Maven clean build.</BR>
3.Run the embedded application spring boot Java class "com.intv.checklist.spring.EmbeddedApplication" </BR>
4.By default it uses in Memory H2  data store from classpath properties file, please change the datasource/dialect properties
  if you have to use persistent data store.</BR>
  
  
Below are the CRUD services to manage checklist and it's action items.</BR>

1.GET - /api/checklist/checklists/{userId}</BR>
2.POST - /api/checklist/create</BR>
3.PUT  -/api/checklist/update</BR>
4.DELETE - /api/checklist/delete/{checklistId}</BR>
5.POST - /api/checklist/actionItem/create</BR>
6.PUT - /api/checklist/actionItem/update</BR>
7.DELETE -/api/checklist/actionItem/delete/{actionItemId}</BR>

