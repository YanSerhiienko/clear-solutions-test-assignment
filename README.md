**Clear Solutions Test Task**

1. All requirements are fully met, the following text is only an additional clarification;
2. Although the data persistence layer was not required, however, I implemented it in this project. I hope this will not cause any inconvenience to the specialist responsible for evaluation of test tasks.
3. In attached resources were described practices for implementing RESTful APIs. I did not follow all of them strictly, for example:  
a) I built endpoints not using nouns (/searchByBirthDate), but using dashes (/search-by-birth-date), because this practice is more readable and more commonly used;  
b) I haven't provided links in JSON responses to navigate through the API (didn't want to overcomplicate API responses).
4. Functionality requirement in clause 2.2 ("update one/some user fields") were implemented in several ways:  
a) UserMapper class and annotation @DynamicUpdate in User entity class;  
b) Separate method for updating field (as example i created methods for updating users phone number, in service and controller classes.
