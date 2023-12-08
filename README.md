# Advance_Group_Assignment
- This is our Group Assignment of Advance Programming
---
# Team Members Name
- Yujen Maharjan
- Aadarsha Shrestha
- Dev Mani Maharjan
- Sagun Karki
- Aslin Karmacharya
- Ronish Shrestha

  ---

# Topic :Automated Citizenship Assessment System for Masathai
- In the effort to unify their nations and establish a new country named Masathai, Malaysia, Singapore, and 
Thailand have decided to create a standardized Citizenship Test for all citizens to take. The purpose of this test 
is to acquaint citizens with the history and culture of Masathai. To accomplish this, an automated MCQ 
Generation, Assessment, and Analysis System is being developed by your group.
Your team's task is to design and implement the automated system for the Citizenship Test, ensuring a fair 
representation of questions from each member's country to create a well-rounded test.

# Key Features

1. Candidate Registration and Authentication:
  - Candidates register their details, including name, birth year, gender, and nationality.
  - Each candidate enters a unique password during registration.
  - Candidate details are stored in a serialized data file for authentication.
2. Examination Module:
  - The Examination Form displays the candidate's name, gender, and national flag.
  - A countdown timer of 5 minutes is shown with audible warnings.
  - The page indicates the current question number out of 20.
  - Navigation buttons allow candidates to move forward and backward through questions.
  - The system presents a variety of question types (text, image-based, image choices).
  - Questions are read from an external file ("question_list.txt").
  - After submitting, candidate answers are recorded in an external delimited file ("test_result.txt").
3. Analysis and Result Presentation:
  - The Analysis Form displays basic statistical results for the sorted test scores.
  - Statistical analyses include maximum, minimum, mean, mode, median, and standard deviation.
  - The com.example.advance_group.result is sorted based on scores, clearly indicating pass/fail markers.
4. Result Display
  - The Result Form provides a drop-down list to select candidates.
  - For each candidate, the form displays the percentage of correct answers.
  - Candidates' answers for each question are presented along with the correct answers.
5. Object-Oriented Concepts:
  - The system maximizes the use of object-oriented concepts like instantiation, encapsulation, 
inheritance, and polymorphism.
6. User Interface
  - The user interface is designed to be aesthetically pleasing and user-friendly.
  - Attention is given to proper coding style, naming conventions, indentation, and comments.
7. Unique Implementation:
  - The system showcases unique features that differentiate it from other groups' applications.
Bonus (Extra Credit):
The candidate registration data is stored in a serialized data file ("candidates.txt")

---

# How To Run This Project
1. Clone This Repository
2. Add sql Querry into your database.
3. Replace sql password to your sql password in Datbaseconnection.java controller
4. Replace all txt file location to your txt file locaion in (Admincontroller.java, QuizPagecontroller.java, Result.java)
5. Run The Project


  
  
