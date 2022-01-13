# Learning Management System 
Learning Management System is a project which is focused on bringing online solution to the educational institutes.
## Project Description
I have developed this project to help students stay up to date with their learning. Due to COVID-19, many students have lost track of their learning as most of the educational institutions were not equipped for online, as COVID-19 came out of the blue. The Application is built in such a way that the learning mode can switched between offline and online by just pressing a toggle button. I have also included Attendance feature which gets updated automatically in online mode by calculating the amount of time a student has spent in the class. Whereas in offline mode, the assigned teacher will have the access to update the attendace in a stipulated time. 
## Admin Application
This application is designed to suit the management of an educational institute. This application has all the rights over the data. The Admin can Read, Write, Delete and Update any data. Admin can also log into any ongoing online class in the Jitsi meet. Admin can communicate with students as well as teachers by in app messages. Admin can upload Students and Teachers data by importing data from an excel sheet. All the users will be authenticated while their data gets uploaded into the database. 
## Tech
1. This project is built on MVVM Architecture.
2. Firebase Authentication is used to ease the user authentication.
3. Firebase Firestore is used to store the data. Firestore rules defined in asuch a way that access is granted by the type of account.
4. Firebase Cloud Messaging is used to send notifications when a message is sent.
5. Jitsi meet to be able to deliver classes online.
## Features
1. Add Students and Teachers
2. View the Students and Teachers
3. View Classes and their details like Proctor, Timetable, Courses, Course materials, Announcements, Exams, Quiz and Result. The Admin has Read, Write, Delete and Update permissions to these features.
4. Students can check their attendance and academic performance.
5. Set Class Monitor in Students section. 
6. Send Message to Students and Teachers.
7. Online Class in Jitsi Meet (Yet to include)

## Sample

https://user-images.githubusercontent.com/49778072/149353909-81753da5-798d-4634-ad22-edb487a5648c.mp4

## External Libraries used:
1. Firebase
2. Circle Image View
3. Butterknife
4. Eventbus
5. Retrofit
6. Jitsi

