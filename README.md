# Widget REST API Version 1.0
This is a REST API to handle types of questions and serve them up for a widget located on a website.

## Prerequisites
JDK8, Maven 3.60+


## How to install and run
Go to the project directory and run them step by step.
```
mvn clean install
jave -jar target/demo-0.0.1-SNAPSHOT.jar
```
After the client successful running, visit http://localhost:8080/Whatever_As_You_Want.

## How to access H2 Database
```$xslt
Database: localhost:8080/h2-console
JDBC URL: jdbc:h2:men:testdb
Username: admin
Password: admin
```
## Database Entities Relationship Design
![Diagram](Diagram.png)

## How to create sample questions
Use **Postman** to create/test HttpRequests

**NOTE**: Set the header **Content-Type** as **application/json**

1. Create a User (Get User UUID)
```
POST
http://localhost:8080/v1/users
{}
```
**Note**: User is generated by the page, so this User is mocked to get the User UUID 

2. Create a Site
```
POST
http://localhost:8080/v1/sites
{
    "url": "www.bob.com"
}
```
3. Create Questions
```$xslt
POST
http://localhost:8080/v1/questions
{
    "type": "Trivia",
    "siteId": 1,
    "question": "How many toes does a pig have?"
}
{
    "type": "Matrix",
    "siteId": 1,
    "question": "Please tell us a bit about yourself?"
}
```
4. Create Choices for Questions
```
POST
http://localhost:8080/v1/questions/1/choices
{
	"choiceText": "3"
}
{
	"choiceText": "4"
}
{
	"choiceText": "The do not have toes silly"
}

POST
http://localhost:8080/v1/questions/2/choices
{
	"choiceTitle": "Age/Gender"
	"choiceText": "< 18"
}
{
	"choiceTitle": "Age/Gender"
	"choiceText": "18 to 35"
}
{
	"choiceTitle": "Age/Gender"
	"choiceText": "35 to 55"
}
{
	"choiceTitle": "Age/Gender"
	"choiceText": "> 55"
}
```
5. Create Options for Questions
```
POST
http://localhost:8080/v1/questions/1/options
{
	"optionText":"Options"
}

POST
http://localhost:8080/v1/questions/2/options
{
	"optionText": "Male"
}
{
	"optionText": "Female"
}
```
6. Create Answers for Questions
```
POST
http://localhost:8080/v1/questions/1/options/1/choices/1
{
	"isCorrectAnswer":false
}
POST
http://localhost:8080/v1/questions/1/options/1/choices/2
{
	"isCorrectAnswer":true
}
POST
http://localhost:8080/v1/questions/1/options/1/choices/3
{
	"isCorrectAnswer":false
}
```
**Note**: The deault value is **False**

## The Requests above will form these questions:
### Trivia Question
> How many toes does a pig have?

|Choices|Optionns|
| :---------- | :---------: |
| 3   | [ ] |
| 4  | [ ] |
| The do not have toes silly | [ ] |

*Only one correct answer with two to four possible answers in this question type.*

### Matrix Question
> Please tell us a bit about yourself? 

| Age/Gender        | Male | Female |
| :----------   | :---------: | :----------: |
| < 18          | [ ] |[ ] |
| 18 to 35      | [ ] |[ ] |
| 35 to 55      | [ ] |[ ] |
| > 55          | [] |[ ] |

*Is an objective question that shows options in a matrix. A visitor can only pick one of the available options, there is no right or wrong answer.*

## **The Question Structure also supports these types of questions**
### Poll Question
> What's your favorite car brand?

|Choices|Options|
| :---------- | :---------: |
| Nissan    | [ ] |
| Honda     | [ ] |
| Audi      | [X] |
| BMW       | [ ] |

*No correct answer with two to four possible answers in this question type.*

### CheckBox Question
> What colors do you like? 

|Choices|Options|
| :---------- | :---------: |
| Red           | [ ] |
| Blue          | [X] |
| Yellow        | [ ] |
| Green         | [ ] |
| Black         | [X] |
| Purple        | [ ] |

*Is an objective question with up to ten possible answers.  This style of question allows for multiple correct responses*

## How to make REST Calls
1. Assign a unique Question for a User At a specific Site
```
GET
http://localhost:8080/v1/assign/{SiteUUID}/{UserUUID}
```  
2. Save User's Result
```
POST
http://localhost:8080/v1/assign/{UserUUID}
{
	"questionId": question_id,
	"questionAnswerId": questionanswer_id
}
```

## Assumptions & Notes
1. The User Entity is only used for test, and one specific User makes sense for all Sites
2. If the User only view the answer without posting his result, the assigned question will not be removed from the candidates list. After posting the result, the assigned question will be removed from the candidates list.
3. **Choices** are regarded as the first column of the question form. The default header is **Choices**.
4. **Options** are regarded as the first row of the question form. The default header is **Options**.
5. The specific QuestionAnswer can be located with **ChoiceId** and **OptionId**, just like coordination in the question form.
6. The XSS filter is used to mitigate XSS attacks.

## Security Considerations
1. The XSS filter has been implemented to mitigate XSS attacks.
2. Web Tokens and Authentication can be used to protect the management of data.

## How to scale & what to do
1. In order to better deployment and testing, the head of Request could add the the currnet version number.
2. Design and Build Specific Errors / Exceptions with Descriptive Response to inform users and builders.
3. When we create questions, if the question form is huge, we have to make requests one by one because the API can only handle one Object instead of the list of JSON or Requests. So that we can build more RequestMapping Method to handle the list of Questions/Sites/Options/Choices/QuestionAnswers to creation.
4. For different types of questions, we can create a abstract class for a abstract Question structure and use specific classes to extend it to create different types of questions with different kinds of attributes.
5. Setting up CI/CD pipeline to improve the efficiency when there are more modules or features.
